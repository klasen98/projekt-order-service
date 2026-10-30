package se.iths.jakob.projektorderservice.dto;

/**
 * En orderrad med namn, pris och antal.
 */
public record OrderItemResponseDto(

        String name,
        double price,
        int quantity
) {
}
