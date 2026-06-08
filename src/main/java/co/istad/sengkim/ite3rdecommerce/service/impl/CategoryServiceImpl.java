package co.istad.sengkim.ite3rdecommerce.service.impl;

import co.istad.sengkim.ite3rdecommerce.dto.CategoryResponse;
import co.istad.sengkim.ite3rdecommerce.dto.CreateCategoryRequest;
import co.istad.sengkim.ite3rdecommerce.dto.UpdateCategoryRequest;
import co.istad.sengkim.ite3rdecommerce.mapper.CategoryMapper;
import co.istad.sengkim.ite3rdecommerce.model.Category;
import co.istad.sengkim.ite3rdecommerce.repository.CategoryRepository;
import co.istad.sengkim.ite3rdecommerce.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper =categoryMapper;
    }

    @Override
    public Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<Category> categoryPage = categoryRepository.findAllByIsDeleted(false,pageable);

        return categoryPage.map(categoryMapper::mapCategoryToCategoryResponse);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        Category isParentExist;
        if(createCategoryRequest.parentCategoryId() == null){
            isParentExist =null;
        }else{
            isParentExist = categoryRepository.findById(createCategoryRequest.parentCategoryId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Parent category not found!"));
        }
        boolean isCategoryExist = categoryRepository.existsByName(createCategoryRequest.name());
        if(isCategoryExist){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Category with this name is already exist.");
        }
        Category category = categoryMapper.mapCreateCategoryRequestToCategory(createCategoryRequest);
        //System generated data
        category.setParentCategory(isParentExist);
        category.setIsDeleted(false);
        category = categoryRepository.save(category);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id).map(categoryMapper::mapCategoryToCategoryResponse).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
    }

    @Override
    public void hardDeleteById(Integer id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));

        categoryRepository.delete(category);
    }

    @Override
    public void softDeleteById(Integer id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        category.setIsDeleted(true);
        softDeleteRecursive(category);
        categoryRepository.save(category);
    }
    private void softDeleteRecursive(Category category) {
        category.setIsDeleted(true);

        if (category.getChildCategories() == null) return;

        for (Category child : category.getChildCategories()) {
            softDeleteRecursive(child);
        }
    }


    @Override
    public List<CategoryResponse> getSubCategories(Integer parentCategoryId) {
        Category category = categoryRepository.findById(parentCategoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        return categoryRepository.findAllByIsDeletedAndParentCategory(false,category).stream().map(categoryMapper::mapCategoryToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with this id."));
        if(category.getIsDeleted()==true){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This category is already deleted.");
        }
        if(updateCategoryRequest.name() !=null){
            category.setName(updateCategoryRequest.name());
        }
        if(updateCategoryRequest.description()!=null){
            category.setDescription(updateCategoryRequest.description());
        }
        if(updateCategoryRequest.icon()!=null){
            category.setIcon(updateCategoryRequest.icon());
        }
        category = categoryRepository.save(category);

        return categoryMapper.mapCategoryToCategoryResponse(category);
    }


}
