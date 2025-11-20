package org.homemade.user.profile.service.model.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductInfoDTO {

    private UUID productId;

    private String name;

    private String brand;

    private String description;

    private String category;

    private BigDecimal price;

    private int unitsInStock;


}
