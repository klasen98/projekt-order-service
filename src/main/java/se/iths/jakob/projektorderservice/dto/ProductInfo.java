package se.iths.jakob.projektorderservice.dto;

public record ProductInfo(
        Long id,
        String name,
        String description,
        double price,
        String stock
) {
}
