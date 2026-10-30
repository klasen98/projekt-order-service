package se.iths.jakob.projektorderservice.dto;

/**
 * Anrop till product-service med id och antal.
 */
public record ProductStockRequest(
        Long productId,
        int quantity

) {
}
