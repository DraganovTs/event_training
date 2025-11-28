package org.homemade.common.event.update.email;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailUserEmailUpdatedRollbackEvent {
    private final UUID emailUserId;
    private final String ownerEmail;
    private final String newOwnerEmail;
}
