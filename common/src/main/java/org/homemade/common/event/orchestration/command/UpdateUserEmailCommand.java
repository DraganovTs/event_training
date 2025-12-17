package org.homemade.common.event.orchestration.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class UpdateUserEmailCommand {
    @TargetAggregateIdentifier
    private final UUID userId;
    private final String email;
    private final String newEmail;
}
