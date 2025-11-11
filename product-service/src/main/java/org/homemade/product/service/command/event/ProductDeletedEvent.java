package org.homemade.product.service.command.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDeletedEvent {

    private UUID productId;
}
