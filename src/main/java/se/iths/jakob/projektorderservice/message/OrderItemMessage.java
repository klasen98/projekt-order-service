package se.iths.jakob.projektorderservice.message;

/**
 * Orderrad i orderbekräftelsemeddelande med namn, antal och pris.
 */
public record OrderItemMessage(
        String name,
        int quantity,
        double price
) {
}
