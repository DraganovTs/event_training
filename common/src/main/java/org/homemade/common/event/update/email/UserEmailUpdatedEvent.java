package org.homemade.common.event.update.email;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class UserEmailUpdatedEvent {
    private final UUID userId;
    private final String email;
    private final String newEmail;
}
