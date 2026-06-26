package co.istad.sengkim.ite3rdecommerce.features.order.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentStatusRequest (
        @NotNull(message = "Status is required.")
        Boolean status
){
}
