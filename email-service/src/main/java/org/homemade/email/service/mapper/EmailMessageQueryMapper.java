package org.homemade.email.service.mapper;

import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.model.enums.EmailStatus;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageQueryMapper {
    public EmailMessage mapEmailMessageCreatedEventToEmailMessage(EmailMessageCreatedEvent event) {
        return EmailMessage.builder()
                .id(event.getMessageId())
                .recipient(event.getRecipient())
                .subject(event.getSubject())
                .body(event.getBody())
                .status(EmailStatus.PENDING)
                .build();
    }

    public EmailMessage mapEmailMessageUpdatedEventToEmailMessage(EmailMessageUpdatedEvent event) {
        return EmailMessage.builder()
                .id(event.getMessageId())
                .recipient(event.getRecipient())
                .subject(event.getSubject())
                .body(event.getBody())
                .status(EmailStatus.PENDING)
                .build();
    }
}
