package org.homemade.product.service.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {



    @NotBlank(message = "Product name must not be blank")
    @Size(max = 30, message = "Product name must be at most 30 characters")
    private String name;

    @Size(max = 30, message = "Brand name must be at most 30 characters")
    private String brand;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotNull(message = "Price must be provided")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal with up to 2 fraction digits")
    private BigDecimal price;

    @Min(value = 0, message = "Units in stock cannot be negative")
    private int unitsInStock;

    private UUID categoryId;

    private UUID ownerId;
}
