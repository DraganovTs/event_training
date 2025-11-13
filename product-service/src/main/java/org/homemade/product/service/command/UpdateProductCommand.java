package org.homemade.product.service.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class UpdateProductCommand {

    @TargetAggregateIdentifier
    private UUID productId;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int unitsInStock;
    private UUID categoryId;
    private UUID ownerId;
}
