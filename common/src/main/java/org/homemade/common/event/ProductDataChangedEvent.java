package org.homemade.common.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDataChangedEvent {

    private UUID productId;
    private String name;
    private String brand;
    private String description;
    private String category;
    private Double price;
    private int unitsInStock;
}
