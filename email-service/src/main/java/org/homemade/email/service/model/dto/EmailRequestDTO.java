package org.homemade.email.service.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmailRequestDTO {

    private UUID id;
    @NotNull(message = "Recipient must not be null")
    @Size(max = 50, message = "Recipient must be at most 50 characters")
    private String recipient;
    @NotNull(message = "Subject must not be null")
    @Size(max = 50, message = "Subject must be at most 100 characters")
    private String subject;
    @NotNull(message = "Body must not be null")
    @Size(max = 1000, message = "Body must be at most 1000 characters")
    private String body;
    @NotNull(message = "Email user id must not be null")
    private UUID emailUserId;

}
