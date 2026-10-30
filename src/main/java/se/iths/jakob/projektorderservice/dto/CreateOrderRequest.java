package se.iths.jakob.projektorderservice.dto;

import java.util.List;

/**
 * En lista med orderrader från en beställning
 */
public record CreateOrderRequest(
        List<CreateOrderItemRequest> items
) {
}
