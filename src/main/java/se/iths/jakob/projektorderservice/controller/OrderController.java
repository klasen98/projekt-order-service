package se.iths.jakob.projektorderservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.jakob.projektorderservice.dto.CreateOrderRequest;
import se.iths.jakob.projektorderservice.dto.OrderResponseDto;
import se.iths.jakob.projektorderservice.model.Order;
import se.iths.jakob.projektorderservice.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String customerEmail = jwt.getSubject();
        OrderResponseDto response = orderService.createOrder(request, customerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }

}