package co.istad.sengkim.ite3rdecommerce.features.product.dto;

import co.istad.sengkim.ite3rdecommerce.features.category.dto.CategorySnippetResponse;

import java.math.BigDecimal;

public record ProductResponse(
        String code,
        String slug,
        String name,
        String description,
        String thumbnail,
        BigDecimal unitPrice,
        Integer qty,
        Boolean isAvailable,
        Boolean isDeleted,
        CategorySnippetResponse categoryResponse
) {
}
