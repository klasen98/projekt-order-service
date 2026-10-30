package se.iths.jakob.projektorderservice.dto;

/**
 * Orderrad i beställning, vilken produkt och hur många.
 */
public record CreateOrderItemRequest(
        Long productId,
        int quantity
) {
}