package co.istad.sengkim.ite3rdecommerce.features.category;


import co.istad.sengkim.ite3rdecommerce.features.category.dto.CategoryResponse;
import co.istad.sengkim.ite3rdecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.sengkim.ite3rdecommerce.features.category.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<CategoryResponse> getAllCategories(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "25") Integer pageSize){
        return categoryService.allCategories(pageNumber,pageSize);
    }


    @GetMapping("/{id}")
     public CategoryResponse getCategoryById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse categoryResponse(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return categoryService.createCategory(createCategoryRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDeleteCategory(@PathVariable Integer id){
        categoryService.hardDeleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteCategory(@PathVariable Integer id,@RequestBody CreateCategoryRequest createCategoryRequest){
        categoryService.softDeleteById(id);
    }

    @GetMapping("/{parentCategoryId}/subcategories")
    public List<CategoryResponse> getAllSubCategories(@PathVariable Integer parentCategoryId){
        return categoryService.getSubCategories(parentCategoryId);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategoryById(@PathVariable Integer id, @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return  categoryService.updateCategoryById(id,updateCategoryRequest);
    }

}
