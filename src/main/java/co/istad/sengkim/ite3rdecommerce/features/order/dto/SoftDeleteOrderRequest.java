package co.istad.sengkim.ite3rdecommerce.features.order.dto;

import jakarta.validation.constraints.NotNull;

public record SoftDeleteOrderRequest(
        @NotNull(message = "isDeleted is required.")
        Boolean isDeleted
) {
}
