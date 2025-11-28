package org.homemade.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class RollbackOwnerEmailCommand {
    @TargetAggregateIdentifier
    private final UUID ownerId;
    private final String ownerEmail;
    private final String newOwnerEmail;
    private final String errorMessage;
}
