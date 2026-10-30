package se.iths.jakob.projektorderservice.dto;

/**
 * Produktinformation med namn, pris, id, beskrivning, pris och antal.
 */
public record ProductInfo(
        Long id,
        String name,
        String description,
        double price,
        String stock
) {
}
