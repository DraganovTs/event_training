package org.homemade.common.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    @Size(max = 100, message = "Street name cannot exceed 100 characters")
    private String street;

    @Size(max = 50, message = "City name cannot exceed 50 characters")
    private String city;

    @Size(max = 50, message = "State name cannot exceed 50 characters")
    private String state;

    @Pattern(regexp = "^[0-9]{4}", message = "Invalid zip code format")
    private String zipCode;
}
