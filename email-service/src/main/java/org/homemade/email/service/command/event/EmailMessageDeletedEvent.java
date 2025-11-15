package org.homemade.email.service.command.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailMessageDeletedEvent {
    private UUID messageId;

}
