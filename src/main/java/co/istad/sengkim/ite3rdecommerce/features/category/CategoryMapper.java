package co.istad.sengkim.ite3rdecommerce.features.category;

import co.istad.sengkim.ite3rdecommerce.features.category.dto.CategoryResponse;
import co.istad.sengkim.ite3rdecommerce.features.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(source = "parentCategory.id", target = "parentId")
    CategoryResponse mapCategoryToCategoryResponse(Category category);
}
