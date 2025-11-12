package org.homemade.product.service.service;

import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductDeletedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.exception.ProductAlreadyExistsException;
import org.homemade.product.service.exception.ProductNotFoundException;
import org.homemade.product.service.mapper.ProductQueryMapper;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.query.FindProductQuery;
import org.homemade.product.service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OwnerService ownerService;
    private final ProductQueryMapper productQueryMapper;
    private final ProductServiceMapper productMapper;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, OwnerService ownerService,
                          ProductQueryMapper productQueryMapper, ProductServiceMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.ownerService = ownerService;
        this.productQueryMapper = productQueryMapper;
        this.productMapper = productMapper;
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

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductByNameAndBrand(FindProductQuery findProductQuery) {
        System.out.println("db try to find product: " + findProductQuery.getName() + " " + findProductQuery.getBrand());
        Product product = productRepository.findByNameAndBrand(findProductQuery.getName(),findProductQuery.getBrand());
        return productMapper.mapProductToProductResponse(product);
    }

    public List<Product> findAllProducts() {
        System.out.println("db try to find all products");
        return productRepository.findAll();
    }
}
