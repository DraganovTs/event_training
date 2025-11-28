package org.homemade.common.event.update.email;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailUserEmailUpdatedEvent {
    private final UUID emailUserId;
    private final String ownerEmail;
    private final String newOwnerEmail;
}
