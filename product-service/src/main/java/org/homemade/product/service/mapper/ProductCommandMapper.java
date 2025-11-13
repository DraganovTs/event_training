package org.homemade.product.service.mapper;

import jakarta.validation.Valid;
import org.homemade.product.service.command.CreateProductCommand;
import org.homemade.product.service.command.UpdateProductCommand;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductCommandMapper {
    public CreateProductCommand mapToCreateProductCommand(@Valid ProductRequestDTO request) {

        return CreateProductCommand.builder()
                .productId(UUID.randomUUID())
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .price(request.getPrice())
                .unitsInStock(request.getUnitsInStock())
                .categoryId(request.getCategoryId())
                .ownerId(request.getOwnerId())
                .build();
    }

    public UpdateProductCommand mapToUpdateProductCommand(@Valid ProductRequestDTO request) {
        return UpdateProductCommand.builder()
                .productId(request.getProductId())
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .price(request.getPrice())
                .unitsInStock(request.getUnitsInStock())
                .categoryId(request.getCategoryId())
                .ownerId(request.getOwnerId())
                .build();
    }
}
