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

    public OrderResponseDto createOrder(CreateOrderRequest request, String customerEmail) {

        // bygg lista till product service
        List<ProductStockRequest> stockRequests = request.items().stream()
                .map(i -> new ProductStockRequest(i.productId(), i.quantity()))
                .toList();

        // anropa productservice
        List<ProductInfo> productinfos = productClient.decreaseStock(stockRequests);

        // bygg order items
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
        order.setCustomerName(customerEmail);
        order.setOrderDate(LocalDateTime.now());
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());

        Order saved = orderRepository.save(order);

        // skicka till rabbitMQ
        List<OrderItemMessage> messageItems = saved.getOrderItems().stream()
                .map(i -> new OrderItemMessage(i.getName(), i.getQuantity(), i.getPrice()))
                .toList();

        OrderConfirmationMessage message = new OrderConfirmationMessage(
                customerEmail,
                messageItems,
                saved.getTotalPrice());

        rabbitTemplate.convertAndSend(emailQueue, message);

        return mapToDto(saved);


    }

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

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }


}