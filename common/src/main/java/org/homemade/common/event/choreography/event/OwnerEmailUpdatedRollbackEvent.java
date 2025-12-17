package org.homemade.common.event.choreography.event;

import lombok.Data;

import java.util.UUID;

@Data
public class OwnerEmailUpdatedRollbackEvent {
    private final UUID ownerId;
    private final String ownerEmail;
    private final String newOwnerEmail;
}
