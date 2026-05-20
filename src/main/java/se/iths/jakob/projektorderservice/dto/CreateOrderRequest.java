package se.iths.jakob.projektorderservice.dto;

import java.util.List;

public record CreateOrderRequest(
        List<CreateOrderRequest> items
) {
}
