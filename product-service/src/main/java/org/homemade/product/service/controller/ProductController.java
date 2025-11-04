package org.homemade.product.service.controller;

import jakarta.validation.Valid;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {
        ProductResponseDTO response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProductsForUser(@RequestParam("userId") UUID userId) {
        List<ProductResponseDTO> ownerProducts = productService.getAllProductsForUser(userId);
        return ResponseEntity.ok(ownerProducts);
    }
}
