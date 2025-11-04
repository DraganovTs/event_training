package org.homemade.product.service.service;

import jakarta.validation.Valid;
import org.homemade.product.service.exception.ProductAlreadyExistsException;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
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
    private final ProductServiceMapper mapper;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, OwnerService ownerService, ProductServiceMapper mapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.ownerService = ownerService;
        this.mapper = mapper;
    }

    @Transactional
    public ProductResponseDTO createProduct(@Valid ProductRequestDTO request) {

        checkProductExist(request.getName());

        Category category = categoryService.getCategoryById(request.getCategoryId());

        Owner owner = ownerService.getOwnerById(request.getOwnerId());

        Product productToSave = mapper.mapProductRequestToProduct(request, category, owner);

        Product savedProduct = productRepository.save(productToSave);

        ProductResponseDTO responseDTO = mapper.mapProductToProductResponse(savedProduct);

        return responseDTO;
    }

    @Transactional(readOnly = true)
    public void checkProductExist(String name) {
        if (productRepository.existsByName(name)) {
            throw new ProductAlreadyExistsException("Product already exists: " + name);
        }
    }

    public List<ProductResponseDTO> getAllProductsForUser(UUID userId) {
        List<Product> ownerProducts = productRepository.findAllByOwner_OwnerId(userId);
        return ownerProducts.stream().map(mapper::mapProductToProductResponse).toList();
    }
}
