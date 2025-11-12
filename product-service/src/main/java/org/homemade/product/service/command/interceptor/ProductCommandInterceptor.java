package org.homemade.product.service.command.interceptor;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.homemade.product.service.command.CreateProductCommand;
import org.homemade.product.service.command.DeleteProductCommand;
import org.homemade.product.service.command.UpdateProductCommand;
import org.homemade.product.service.exception.CategoryNotFoundException;
import org.homemade.product.service.exception.OwnerNotFoundException;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.repository.CategoryRepository;
import org.homemade.product.service.repository.OwnerRepository;
import org.homemade.product.service.repository.ProductRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class ProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OwnerRepository ownerRepository;

    public ProductCommandInterceptor(ProductRepository productRepository, CategoryRepository categoryRepository, OwnerRepository ownerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.ownerRepository = ownerRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, commandMessage) -> {
            if (CreateProductCommand.class.equals(commandMessage.getPayloadType())) {
                CreateProductCommand command = (CreateProductCommand) commandMessage.getPayload();
                Optional<Product> optionalProduct = productRepository.findByNameAndOwner_OwnerId(command.getName(),
                        command.getOwner());
                if (optionalProduct.isPresent()) {
                    throw new org.homemade.product.service.exception.ProductAlreadyExistsException("product already exist with name: " + command.getName());
                }
                Optional<Category> optionalCategory = categoryRepository.findById(command.getCategory());
                if (optionalCategory.isEmpty()) {
                    throw new CategoryNotFoundException("category not found with id: " + command.getCategory());
                }
                Optional<org.homemade.product.service.model.entity.Owner> optionalOwner = ownerRepository.findById(command.getOwner());
                if (optionalOwner.isEmpty()) {
                    throw new OwnerNotFoundException("owner not found with id: " + command.getOwner());
                }
            } else if (commandMessage.getPayloadType().equals(UpdateProductCommand.class)) {
                UpdateProductCommand command = (UpdateProductCommand) commandMessage.getPayload();
                Optional<Product> optionalProduct = productRepository.findByNameAndOwner_OwnerId(command.getName(),
                        command.getOwner());
                if (optionalProduct.isEmpty()) {
                    throw new org.homemade.product.service.exception.ProductNotFoundException("product not found with name: " + command.getName());
                }
                Optional<Category> optionalCategory = categoryRepository.findById(command.getCategory());
                if (optionalCategory.isEmpty()) {
                    throw new CategoryNotFoundException("category not found with id: " + command.getCategory());
                }
                Optional<Owner> optionalOwner = ownerRepository.findById(command.getOwner());
                if (optionalOwner.isEmpty()) {
                    throw new OwnerNotFoundException("owner not found with id: " + command.getOwner());
                }
            } else if (commandMessage.getPayloadType().equals(DeleteProductCommand.class)) {
                DeleteProductCommand command = (DeleteProductCommand) commandMessage.getPayload();
                Optional<Product> optionalProduct = productRepository.findById(command.getProductId());
                if (optionalProduct.isEmpty()) {
                    throw new org.homemade.product.service.exception.ProductNotFoundException("product not found with id: " + command.getProductId());
                }
            }

            return commandMessage;
        };

    }
}
