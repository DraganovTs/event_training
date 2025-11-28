package org.homemade.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class RollbackEmailUserCommand {
    @TargetAggregateIdentifier
    private final UUID emailUserId;
    private final String ownerEmail;
    private final String newOwnerEmail;
    private final String errorMessage;
}
