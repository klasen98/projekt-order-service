package se.iths.jakob.projektorderservice.message;

import java.util.List;

/**
 * Meddelande som skicka till RabbitMQ när en order har lagts med kundens mail, orderrader och totalpris.
 */
public record OrderConfirmationMessage(
        String customerEmail,
        List<OrderItemMessage> items,
        double totalPrice
) {
}
