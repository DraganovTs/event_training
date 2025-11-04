package org.homemade.gateway.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.homemade.common.model.dto.ProductResponseDTO;
import org.homemade.common.model.dto.UserResponseDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class SummaryDTO {

    private UserResponseDTO user;
    private List<ProductResponseDTO> products;

}
