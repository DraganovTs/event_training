package org.homemade.product.service.service;

import org.homemade.product.service.exception.CategoryAlreadyExistsException;
import org.homemade.product.service.exception.CategoryNotFoundException;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.common.model.dto.CategoryDTO;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductServiceMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, ProductServiceMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(
                "Category not found whit Id: " + categoryId
        ));
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO request) {

        checkCategoryExist(request.getCategoryName());
        System.out.println("db try to save category: " + request.getCategoryName());
        Category categoryToSave = mapper.mapCategoryDTOtoCategory(request);
        Category savedCategory = categoryRepository.save(categoryToSave);
        return mapper.mapCategoryToCategoryDTO(savedCategory);

    }

    @Transactional(readOnly = true)
    public void checkCategoryExist(String categoryName) {
        if (categoryRepository.existsByCategoryName(categoryName)){
            throw new CategoryAlreadyExistsException("Category already exists: " + categoryName);
        }
    }
}
