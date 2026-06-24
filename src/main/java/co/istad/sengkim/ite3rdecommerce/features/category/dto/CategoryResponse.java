package co.istad.sengkim.ite3rdecommerce.features.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon,
        Integer parentId
) {
}
