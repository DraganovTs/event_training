package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class OwnerEmailUpdatedEvent {
    private  UUID ownerId;
    private  String ownerEmail;
    private  String newOwnerEmail;
}
