package org.homemade.product.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.product.service.command.event.ProductCreatedEvent;
import org.homemade.product.service.command.event.ProductDeletedEvent;
import org.homemade.product.service.command.event.ProductUpdatedEvent;
import org.homemade.product.service.service.ProductService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductProjection {

    private final ProductService productService;

    public ProductProjection(ProductService productService) {
        this.productService = productService;
    }


    @EventHandler
    public void on(ProductCreatedEvent event) {
        System.out.println("Handling ProductCreatedEvent for: " + event.getProductId());
        productService.createProduct(event);
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        System.out.println("Handling ProductDeletedEvent for: " + event.getProductId());
        productService.updateProduct(event);
    }

    @EventHandler
    public void on(ProductDeletedEvent event) {
        productService.deleteProduct(event);
    }
}
