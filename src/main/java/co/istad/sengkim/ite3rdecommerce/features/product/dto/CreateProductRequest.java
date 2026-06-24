package co.istad.sengkim.ite3rdecommerce.features.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name is required!")
        @Size(max = 255)
        String name,
        @Size(max = 500)
        String description,
        @Size(max = 500)
        String thumbnail,
        @NotNull(message = "Unit price is required")
        @Min(0)
        BigDecimal unitPrice,
        @NotNull(message = "Quantity is required")
        @Min(0)
        Integer qty,
        @NotNull(message = "Category id is required")
        @Positive
        Integer categoryId
) {
}
