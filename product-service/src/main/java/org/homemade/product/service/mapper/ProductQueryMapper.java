package org.homemade.product.service.mapper;

import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.model.entity.Category;
import org.homemade.product.service.model.entity.Owner;
import org.homemade.product.service.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductQueryMapper {
    public Product mapProductCreatedEventToProduct(ProductCreatedEvent event, Category category, Owner owner) {
        System.out.println("mapper try to save product: " + event.getName());
        return Product.builder()
                .productId(event.getProductId())
                .name(event.getName())
                .brand(event.getBrand())
                .description(event.getDescription())
                .price(event.getPrice())
                .unitsInStock(event.getUnitsInStock())
                .category(category)
                .owner(owner)
                .build();
    }

    public Product mapProductUpdateEventToProduct(ProductUpdatedEvent event, Category category, Owner owner) {
        return Product.builder()
                .productId(event.getProductId())
                .name(event.getName())
                .brand(event.getBrand())
                .description(event.getDescription())
                .price(event.getPrice())
                .unitsInStock(event.getUnitsInStock())
                .category(category)
                .owner(owner)
                .build();
    }
}
