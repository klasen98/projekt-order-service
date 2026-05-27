package se.iths.jakob.projektorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.iths.jakob.projektorderservice.client.ProductClient;
import se.iths.jakob.projektorderservice.dto.*;
import se.iths.jakob.projektorderservice.message.OrderConfirmationMessage;
import se.iths.jakob.projektorderservice.message.OrderItemMessage;
import se.iths.jakob.projektorderservice.model.Order;
import se.iths.jakob.projektorderservice.model.OrderItem;
import se.iths.jakob.projektorderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.email}")
    private String emailQueue;

    public OrderResponseDto createOrder(CreateOrderRequest request, String customerEmail, String bearerToken) {

        // bygg lista till product service
        List<ProductStockRequest> stockRequests = request.items().stream()
                .map(i -> new ProductStockRequest(i.productId(), i.quantity()))
                .toList();

        // anropa product service metoden
        List<ProductInfo> productinfos = productClient.decreaseStock(stockRequests, bearerToken);

        // skapar orderrader och sparar en snapshot
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < productinfos.size(); i++) {
            ProductInfo Info = productinfos.get(i);
            int quan = request.items().get(i).quantity();

            OrderItem item = new OrderItem();
            item.setName(Info.name());
            item.setPrice(Info.price());
            item.setQuantity(quan);
            orderItems.add(item);

        }
        // bygga ordern
        Order order = new Order();
        order.setCustomerName(customerEmail);  // kundens mail
        order.setOrderDate(LocalDateTime.now()); // dagens datum

        // skapar en unik orderrad till ordern
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);

        // totala priset på ordern
        order.setTotalPrice(orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());

        Order saved = orderRepository.save(order);

        // skicka till rabbitMQ
        List<OrderItemMessage> messageItems = saved.getOrderItems().stream()
                .map(i -> new OrderItemMessage(i.getName(), i.getQuantity(), i.getPrice()))
                .toList();

        // skapar ett meddelande
        OrderConfirmationMessage message = new OrderConfirmationMessage(
                customerEmail,
                messageItems,
                saved.getTotalPrice());

        // Skickar till rabbitMQ kön
        rabbitTemplate.convertAndSend(emailQueue, message);

        // mappar till dto
        return mapToDto(saved);


    }

    // hjälp metod för att skapa en order från databasen
    private OrderResponseDto mapToDto(Order order) {
        List<OrderItemResponseDto> items = order.getOrderItems().stream()
                .map(i -> new OrderItemResponseDto(i.getName(), i.getPrice(), i.getQuantity()))
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getCustomerName(),
                order.getTotalPrice(),
                items
        );
    }

    // hämtar alla orders och mappar dem till dto
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }


}