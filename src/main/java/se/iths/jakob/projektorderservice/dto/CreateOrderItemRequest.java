package se.iths.jakob.projektorderservice.dto;

public record CreateOrderItemRequest(
        Long productId,
        int quantity
) {
}