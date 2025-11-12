package org.homemade.product.service.service;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.homemade.product.service.command.CreateProductCommand;
import org.homemade.product.service.command.DeleteProductCommand;
import org.homemade.product.service.command.UpdateProductCommand;
import org.homemade.product.service.mapper.ProductCommandMapper;
import org.homemade.product.service.model.dto.ProductRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductCommandService {

    private final CommandGateway commandGateway;
    private final ProductCommandMapper productCommandMapper;

    public ProductCommandService(CommandGateway commandGateway, ProductCommandMapper productCommandMapper) {
        this.commandGateway = commandGateway;
        this.productCommandMapper = productCommandMapper;
    }


    public void createProduct(@Valid ProductRequestDTO request) {
        CreateProductCommand command = productCommandMapper.mapToCreateProductCommand(request);
        commandGateway.sendAndWait(command);
    }

    public void updateProduct(@Valid ProductRequestDTO request) {
        UpdateProductCommand command = productCommandMapper.mapToUpdateProductCommand(request);
        commandGateway.sendAndWait(command);
    }

    public void deleteProduct(UUID productId) {
        DeleteProductCommand command = DeleteProductCommand.builder()
                .productId(productId)
                .build();
        commandGateway.sendAndWait(command);

    }
}
