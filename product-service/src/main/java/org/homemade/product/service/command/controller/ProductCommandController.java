package org.homemade.product.service.command.controller;

import jakarta.validation.Valid;
import org.homemade.common.model.dto.ResponseDTO;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.homemade.product.service.service.ProductCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductCommandController {

    private final ProductCommandService productCommandService;

    public ProductCommandController(ProductCommandService productCommandService) {
        this.productCommandService = productCommandService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {

        productCommandService.createProduct(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .status("Created")
                        .message("Product created successfully")
                        .build());
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateProduct(@Valid @RequestBody ProductRequestDTO request) {
        productCommandService.updateProduct(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Updated")
                        .message("Product updated successfully")
                        .build());
    }

    @PatchMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteUser(@RequestParam("productId") UUID productId) {

        productCommandService.deleteProduct(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status("Deleted")
                        .message("Product deleted successfully")
                        .build());
    }
}
