package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailUserEmailUpdatedEvent {
    private  UUID emailUserId;
    private  String ownerEmail;
    private  String newOwnerEmail;
}
