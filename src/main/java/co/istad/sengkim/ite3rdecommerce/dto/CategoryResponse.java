package co.istad.sengkim.ite3rdecommerce.dto;

import co.istad.sengkim.ite3rdecommerce.model.Category;
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
