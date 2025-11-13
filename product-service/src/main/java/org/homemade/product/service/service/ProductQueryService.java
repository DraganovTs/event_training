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
    private final ProductServiceMapper productMapper;

    public ProductQueryService(QueryGateway queryGateway, ProductServiceMapper productMapper) {
        this.queryGateway = queryGateway;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO findUserQuery(String name, String brand) {
        FindProductQuery findProductQuery = new FindProductQuery(name, brand);
        return productMapper
                .mapProductToProductResponse(queryGateway.query(findProductQuery, ResponseTypes.instanceOf(Product.class)).join());
    }

    public List<ProductResponseDTO> getAllProducts() {
        return queryGateway
                .query(new FindAllProductsQuery(), ResponseTypes.multipleInstancesOf(Product.class))
                .join()
                .stream()
                .map(productMapper::mapProductToProductResponse)
                .toList();
    }
}
