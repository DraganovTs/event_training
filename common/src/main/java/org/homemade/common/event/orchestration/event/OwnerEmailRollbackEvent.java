package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class OwnerEmailRollbackEvent {
    private final UUID ownerId;
    private final String ownerEmail;
    private final String newOwnerEmail;
    private final String errorMessage;

}
