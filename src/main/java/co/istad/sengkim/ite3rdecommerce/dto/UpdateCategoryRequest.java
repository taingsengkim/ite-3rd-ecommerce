package co.istad.sengkim.ite3rdecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @Size(max = 50)
        String name,
        @Size(max = 250)
        String description,
        String icon
) {
}
