package org.homemade.product.service.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryDTO {

    private UUID categoryId;
    @NotBlank(message = "Category name must not be blank")
    @Size(max = 30, message = "Category name must be at most 30 characters")
    private String categoryName;


}
