package org.homemade.email.service.command.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailMessageCreatedEvent {
    private UUID messageId;
    private String recipient;
    private String subject;
    private String body;
    private UUID emailUserId;
}
