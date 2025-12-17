package org.homemade.common.event.orchestration.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserEmailRollbackEvent {
    private final UUID userId;
    private final String email;
    private final String newEmail;
    private final String errorMessage;
}
