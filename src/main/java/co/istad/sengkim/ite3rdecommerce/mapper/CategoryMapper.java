package co.istad.sengkim.ite3rdecommerce.mapper;

import co.istad.sengkim.ite3rdecommerce.dto.CategoryResponse;
import co.istad.sengkim.ite3rdecommerce.dto.CreateCategoryRequest;
import co.istad.sengkim.ite3rdecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest);

    @Mapping(source = "parentCategory.id", target = "parentId")
    CategoryResponse mapCategoryToCategoryResponse(Category category);
}
