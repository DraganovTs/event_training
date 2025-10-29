package org.homemade.product.service.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OwnerDTO {

    private UUID ownerId;
    @NotBlank(message = "Owner name must not be blank")
    @Size(max = 30, message = "Owner name must be at most 30 characters")
    @Column(name = "owner_name")
    private String ownerName;
    @Email(message = "Owner email should be valid")
    @NotBlank(message = "Owner email must not be blank")
    @Size(max = 30, message = "Owner email must be at most 30 characters")
    private String ownerEmail;

}
