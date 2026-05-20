package se.iths.jakob.projektorderservice.message;

import java.util.List;

public record OrderConfirmationMessage(
        String customerEmail,
        List<OrderItemMessage> items,
        double totalPrice
) {
}
