package org.homemade.common.event.choreography.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailUserEmailUpdatedEvent {
    private  UUID emailUserId;
    private  String ownerEmail;
    private  String newOwnerEmail;
}
