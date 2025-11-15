package org.homemade.product.service.query.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.query.FindAllProductsQuery;
import org.homemade.product.service.query.FindProductQuery;
import org.homemade.product.service.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQueryHandler {

    private final ProductService productService;

    public ProductQueryHandler(ProductService productService) {
        this.productService = productService;
    }

    @QueryHandler
    public Product findProduct(FindProductQuery findProductQuery) {
        return productService.getProductByNameAndBrand(findProductQuery);
    }

    @QueryHandler
    public List<Product> findAllProducts(FindAllProductsQuery findAllProductsQuery) {
        return productService.findAllProducts();
    }
}
