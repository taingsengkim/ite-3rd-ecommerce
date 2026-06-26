package co.istad.sengkim.ite3rdecommerce.features.order.orderline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineDto(
        @NotBlank(message = "Code is required")
        String code,
        @Positive(message = "Quantity can't be negative")
        @NotNull(message = "Quantity is required")
        Integer qty,
        @Positive(message = "Unit price can't be negative")
        @NotNull(message = "Unit price is required")
        Double unitPrice
) {
}
