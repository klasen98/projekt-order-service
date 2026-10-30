package se.iths.jakob.projektorderservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.iths.jakob.projektorderservice.dto.CreateOrderRequest;
import se.iths.jakob.projektorderservice.dto.OrderResponseDto;
import se.iths.jakob.projektorderservice.service.OrderService;

import java.util.List;

/**
 * REST-controller för orderrelaterade endpoints.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Skapar en order med jwt som sedan skickas vidare till product-service.
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody CreateOrderRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        // strängen vi skickar till product service
        String bearerToken = "Bearer " + jwt.getTokenValue();

        // kundens mail
        String customerEmail = jwt.getSubject();

        // anropar service metoden
        OrderResponseDto response = orderService.createOrder(request, customerEmail, bearerToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Hämtar alla ordrar
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


}

