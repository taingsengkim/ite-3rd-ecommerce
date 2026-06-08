package co.istad.sengkim.ite3rdecommerce.service;

import co.istad.sengkim.ite3rdecommerce.dto.CategoryResponse;
import co.istad.sengkim.ite3rdecommerce.dto.CreateCategoryRequest;
import co.istad.sengkim.ite3rdecommerce.dto.UpdateCategoryRequest;
import org.hibernate.boot.model.naming.ImplicitCollectionTableNameSource;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoryService {
    Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize);
    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteById(Integer id);
    void softDeleteById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);
}
