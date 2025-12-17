package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class OwnerEmailRollbackEvent {
    private  UUID ownerId;
    private  String ownerEmail;
    private  String newOwnerEmail;
    private  String errorMessage;

}
