package org.homemade.email.service.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class DeleteEmailMessageCommand {

    @TargetAggregateIdentifier
    private final UUID messageId;
}
