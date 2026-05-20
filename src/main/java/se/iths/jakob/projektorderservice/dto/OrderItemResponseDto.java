package se.iths.jakob.projektorderservice.dto;

public record OrderItemResponseDto(

        String name,
        double price,
        int quantity
) {
}
