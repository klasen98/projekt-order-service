package se.iths.jakob.projektorderservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDateTime orderDate,
        String customerName,
        double totalPrice,
        List<OrderItemResponseDto> orderItems
) {
}
