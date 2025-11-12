package org.homemade.product.service.service;

import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductDeletedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.exception.ProductAlreadyExistsException;
import org.homemade.product.service.exception.ProductNotFoundException;
import org.homemade.product.service.mapper.ProductQueryMapper;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OwnerService ownerService;
    private final ProductQueryMapper productQueryMapper;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, OwnerService ownerService,
                          ProductQueryMapper productQueryMapper
    ) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.ownerService = ownerService;
        this.productQueryMapper = productQueryMapper;
    }

    @Transactional
    public void createProduct(ProductCreatedEvent event) {
        System.out.println("db try to save product: " + event.getName());
        checkProductExist(event.getName(), event.getBrand());
        Category category = categoryService.getCategoryById(event.getCategoryId());
        Owner owner = ownerService.getOwnerById(event.getOwnerId());
        Product productToSave = productQueryMapper.mapProductCreatedEventToProduct(event, category, owner);
        productRepository.save(productToSave);
    }

    @Transactional
    public void updateProduct(ProductUpdatedEvent event) {
        System.out.println("db try to update product: " + event.getName());
        checkProductExistById(event.getProductId());
        Category category = categoryService.getCategoryById(event.getCategoryId());
        Owner owner = ownerService.getOwnerById(event.getOwnerId());
        Product productToSave = productQueryMapper.mapProductUpdateEventToProduct(event, category, owner);
        productRepository.save(productToSave);
    }

    @Transactional
    public void deleteProduct(ProductDeletedEvent event) {
        System.out.println("db try to delete product: " + event.getProductId());
        checkProductExistById(event.getProductId());
        productRepository.deleteById(event.getProductId());
    }


    @Transactional
    public void checkProductExistById(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }
    }

    @Transactional(readOnly = true)
    public void checkProductExist(String name, String brand) {
        if (productRepository.existsByNameAndBrand(name, brand)) {
            throw new ProductAlreadyExistsException("Product already exists: " + name);
        }
    }

}
