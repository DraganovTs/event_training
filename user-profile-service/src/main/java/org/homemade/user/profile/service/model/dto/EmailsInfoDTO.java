package org.homemade.user.profile.service.model.dto;



import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmailsInfoDTO {

    private UUID emailId;
    private String recipient;
    private String subject;
    private String body;
    private String status;

}
