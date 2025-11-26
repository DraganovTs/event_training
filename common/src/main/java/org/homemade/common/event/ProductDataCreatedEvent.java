package org.homemade.common.event;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDataCreatedEvent {
    private UUID productId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int unitsInStock;
    private UUID categoryId;
    private UUID ownerId;
}
