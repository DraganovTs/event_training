package org.homemade.gateway.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SummaryDTO {

    private UserDTO user;
    private List<ProductDTO> products;

}
