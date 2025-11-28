package org.homemade.common.event.update.email;

import lombok.Data;

import java.util.UUID;

@Data
public class OwnerEmailUpdatedEvent {
    private final UUID ownerId;
    private final String ownerEmail;
    private final String newOwnerEmail;
}
