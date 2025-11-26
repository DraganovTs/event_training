package org.homemade.user.profile.service.mapper;

import org.homemade.common.event.ProductDataChangedEvent;
import org.homemade.common.event.ProductDataCreatedEvent;
import org.homemade.user.profile.service.model.entity.ProductInfo;
import org.homemade.user.profile.service.model.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductInfoMapper {
    public ProductInfo mapPrductDataCreatedEventToProductInfo(ProductDataCreatedEvent event, UserProfile userProfile) {
        return ProductInfo.builder()
                .productId(event.getProductId())
                .name(event.getName())
                .brand(event.getBrand())
                .description(event.getDescription())
                .category(event.getCategoryId().toString())
                .price(event.getPrice())
                .unitsInStock(event.getUnitsInStock())
                .userProfile(userProfile)
                .build();
    }

    public ProductInfo mapProductDataChangedEventToProductInfo(ProductDataChangedEvent event, ProductInfo productInfo) {
        return ProductInfo.builder()
                .productId(productInfo.getProductId())
                .name(event.getName())
                .brand(event.getBrand())
                .description(event.getDescription())
                .category(event.getCategory().toString())
                .price(event.getPrice())
                .unitsInStock(event.getUnitsInStock())
                .build();
    }
}
