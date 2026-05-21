package se.iths.jakob.projektorderservice.dto;

public record ProductStockRequest(
        Long productId,
        int quantity

) {
}
