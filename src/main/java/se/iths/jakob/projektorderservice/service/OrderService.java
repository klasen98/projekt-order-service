package se.iths.jakob.projektorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import se.iths.jakob.projektorderservice.client.ProductClient;
import se.iths.jakob.projektorderservice.repository.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderItemRepository orderItemRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ProductClient productClient;


}
