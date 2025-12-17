package org.homemade.common.event.choreography.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserEmailUpdatedEvent {
    private final UUID userId;
    private final String email;
    private final String newEmail;
}
