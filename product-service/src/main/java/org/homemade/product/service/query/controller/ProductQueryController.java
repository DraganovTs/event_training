package org.homemade.product.service.query.controller;

import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.service.ProductQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping("/nameAndBrand")
    public ResponseEntity<ProductResponseDTO> getProductByNameAndBrand(@RequestParam String name, @RequestParam String brand) {
        ProductResponseDTO productResponseDTO = productQueryService.findUserQuery(name, brand);
        return ResponseEntity.ok(productResponseDTO);

    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> response = productQueryService.getAllProducts();

        return ResponseEntity.ok(response);
    }
}
