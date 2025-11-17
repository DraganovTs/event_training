package org.homemade.email.service.mapper;

import org.homemade.email.service.model.dto.EmailMessageResponseDTO;
import org.homemade.email.service.model.entity.EmailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageServiceMapper {

    public EmailMessageResponseDTO mapEmailMessageToEmailMessageResponse(EmailMessage emailMessage) {
        return EmailMessageResponseDTO.builder()
                .id(emailMessage.getId())
                .recipient(emailMessage.getRecipient())
                .subject(emailMessage.getSubject())
                .status(emailMessage.getStatus())
                .createdAt(emailMessage.getCreatedAt())
                .sentAt(emailMessage.getSentAt())
                .build();
    }
}
