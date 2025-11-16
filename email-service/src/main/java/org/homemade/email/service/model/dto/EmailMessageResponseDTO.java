package org.homemade.email.service.model.dto;


import lombok.Builder;
import lombok.Data;
import org.homemade.email.service.model.enums.EmailStatus;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class EmailMessageResponseDTO {


    private UUID id;
    private String recipient;
    private String subject;
    private String body;
    private EmailStatus status;
    private Date createdAt;
    private Date sentAt;
}
