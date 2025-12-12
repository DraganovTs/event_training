package org.homemade.product.service.service;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.product.service.mapper.ProductServiceMapper;
import org.homemade.product.service.model.entity.Product;
import org.homemade.product.service.query.FindAllProductsQuery;
import org.homemade.product.service.query.FindProductQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    private final QueryGateway queryGateway;
    private final ProductServiceMapper productServiceMapper;

    public ProductQueryService(QueryGateway queryGateway, ProductServiceMapper productServiceMapper) {
        this.queryGateway = queryGateway;
        this.productServiceMapper = productServiceMapper;
    }

    public ProductResponseDTO findUserQuery(String name, String brand) {
        return findByNameAndBrand(name, brand);
    }

    public ProductResponseDTO findByNameAndBrand(String name, String brand) {
        Product product = queryForSingleProduct(new FindProductQuery(name, brand));
        return productServiceMapper.mapProductToProductResponse(product);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return queryForAllProducts(new FindAllProductsQuery())
                .stream()
                .map(productServiceMapper::mapProductToProductResponse)
                .toList();
    }

    private Product queryForSingleProduct(FindProductQuery query) {
        return queryGateway
                .query(query, ResponseTypes.instanceOf(Product.class))
                .join();
    }

    private List<Product> queryForAllProducts(FindAllProductsQuery query) {
        return queryGateway
                .query(query, ResponseTypes.multipleInstancesOf(Product.class))
                .join();
    }
}
