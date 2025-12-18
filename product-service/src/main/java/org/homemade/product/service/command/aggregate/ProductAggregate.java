package org.homemade.product.service.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.homemade.common.event.ProductDataChangedEvent;
import org.homemade.common.event.ProductDataCreatedEvent;
import org.homemade.common.event.orchestration.command.UpdateOwnerEmailCommand;
import org.homemade.common.event.orchestration.event.OwnerEmailUpdatedEvent;
import org.homemade.product.service.command.CreateProductCommand;
import org.homemade.product.service.command.DeleteProductCommand;
import org.homemade.product.service.command.UpdateProductCommand;
import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductDeletedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.query.FindProductQuery;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
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
    public ProductAggregate(CreateProductCommand command) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(command, productCreatedEvent);
        ProductDataCreatedEvent productDataCreatedEvent = new ProductDataCreatedEvent();
        BeanUtils.copyProperties(command, productDataCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent).andThen(
                () -> AggregateLifecycle.apply(productDataCreatedEvent)
        );
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
    public void handle(UpdateProductCommand command) {
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        BeanUtils.copyProperties(command, productUpdatedEvent);
        ProductDataChangedEvent productDataChangedEvent = new ProductDataChangedEvent();
        BeanUtils.copyProperties(command, productDataChangedEvent);
        AggregateLifecycle.apply(productUpdatedEvent).andThen(
                () -> AggregateLifecycle.apply(productDataChangedEvent)
        );
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
    public void handle(DeleteProductCommand deleteProductCommand) {
        ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
        BeanUtils.copyProperties(deleteProductCommand, productDeletedEvent);
        AggregateLifecycle.apply(productDeletedEvent);
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent productDeletedEvent) {
        this.productId = productDeletedEvent.getProductId();
    }


    @CommandHandler
    public void handle(UpdateOwnerEmailCommand updateOwnerEmailCommand) {
        OwnerEmailUpdatedEvent ownerEmailUpdatedEvent = new OwnerEmailUpdatedEvent();
        BeanUtils.copyProperties(updateOwnerEmailCommand, ownerEmailUpdatedEvent);
        AggregateLifecycle.apply(ownerEmailUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(OwnerEmailUpdatedEvent ownerEmailUpdatedEvent) {
        this.owner = ownerEmailUpdatedEvent.getOwnerId();
    }

}
