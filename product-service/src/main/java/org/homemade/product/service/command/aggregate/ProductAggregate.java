package org.homemade.product.service.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.homemade.product.service.command.CreateProductCommand;
import org.homemade.product.service.command.DeleteProductCommand;
import org.homemade.product.service.command.UpdateProductCommand;
import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductDeletedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.exception.ProductAlreadyExistsException;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.repository.ProductRepository;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private UUID productId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int unitsInStock;
    private UUID category;
    private UUID owner;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command, ProductRepository productRepository) {
        Optional<Product> optionalProduct = productRepository.findByNameAndOwner_OwnerId(command.getName(),
                command.getOwnerId());

        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyExistsException("product already exist with name: " + command.getName());
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(command, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.name = productCreatedEvent.getName();
        this.brand = productCreatedEvent.getBrand();
        this.description = productCreatedEvent.getDescription();
        this.price = productCreatedEvent.getPrice();
        this.unitsInStock = productCreatedEvent.getUnitsInStock();
        this.category = productCreatedEvent.getCategoryId();
        this.owner = productCreatedEvent.getOwnerId();
    }

    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand){
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent productUpdatedEvent) {
       this.productId = productUpdatedEvent.getProductId();
       this.name = productUpdatedEvent.getName();
       this.brand = productUpdatedEvent.getBrand();
       this.description = productUpdatedEvent.getDescription();
       this.price = productUpdatedEvent.getPrice();
       this.unitsInStock = productUpdatedEvent.getUnitsInStock();
       this.category = productUpdatedEvent.getCategoryId();
       this.owner = productUpdatedEvent.getOwnerId();
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand){
        ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
        BeanUtils.copyProperties(deleteProductCommand, productDeletedEvent);
        AggregateLifecycle.apply(productDeletedEvent);
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productId = productDeletedEvent.getProductId();
    }


}
