package org.homemade.common.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailUpdateDTO {
    private UUID userId;
    private String oldEmail;
    private String newEmail;
}
