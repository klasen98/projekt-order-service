package se.iths.jakob.projektorderservice.message;

public record OrderItemMessage(
        String name,
        int quantity,
        double price
) {
}
