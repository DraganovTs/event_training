package org.homemade.product.service.mapper;

import jakarta.validation.Valid;
import org.homemade.common.model.dto.CategoryDTO;
import org.homemade.common.model.dto.OwnerDTO;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductServiceMapper {
    public Product mapProductRequestToProduct(@Valid ProductRequestDTO request, Category category, Owner owner) {
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .price(request.getPrice())
                .unitsInStock(request.getUnitsInStock())
                .category(category)
                .owner(owner)
                .build();
    }

    public ProductResponseDTO mapProductToProductResponse(Product productToSave) {
        return ProductResponseDTO.builder()
                .productId(productToSave.getProductId())
                .name(productToSave.getName())
                .brand(productToSave.getBrand())
                .description(productToSave.getDescription())
                .price(productToSave.getPrice())
                .unitsInStock(productToSave.getUnitsInStock())
                .dateCreated(productToSave.getDateCreated())
                .lastUpdated(productToSave.getLastUpdated())
                .category(mapCategoryToCategoryDTO(productToSave.getCategory()))
                .owner(mapOwnerToOwnerDTO(productToSave.getOwner()))
                .build();
    }

    public OwnerDTO mapOwnerToOwnerDTO(Owner owner) {
        return OwnerDTO.builder()
                .ownerId(owner.getOwnerId())
                .ownerName(owner.getOwnerName())
                .ownerEmail(owner.getOwnerEmail())
                .build();
    }

    public Owner mapOwnerDTOToOwner(OwnerDTO request) {
        return Owner.builder()
                .ownerId(request.getOwnerId())
                .ownerName(request.getOwnerName())
                .ownerEmail(request.getOwnerEmail())
                .products(new ArrayList<>())
                .build();
    }

    public CategoryDTO mapCategoryToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }


    public Category mapCategoryDTOtoCategory(CategoryDTO request) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .products(new ArrayList<>())
                .build();
    }
}
