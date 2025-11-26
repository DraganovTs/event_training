package org.homemade.user.profile.service.service;

import org.homemade.user.profile.service.exception.ProductInfoNotFoundException;
import org.homemade.user.profile.service.model.entity.ProductInfo;
import org.homemade.user.profile.service.repository.ProductInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    public ProductInfoService(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    public void saveProductInfo(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }

    public ProductInfo getProductById(UUID productId) {
        return productInfoRepository.findById(productId).orElseThrow(
                () -> new ProductInfoNotFoundException("Product whit not found whit id: " + productId)
        );
    }
}
