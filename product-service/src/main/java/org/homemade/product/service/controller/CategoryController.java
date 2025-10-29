package org.homemade.product.service.controller;

import jakarta.validation.Valid;
import org.homemade.product.service.model.dto.CategoryDTO;
import org.homemade.product.service.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO request) {
        CategoryDTO response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }
}
