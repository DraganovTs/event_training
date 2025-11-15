package org.homemade.email.service.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class CreateEmailMessageCommand {

    @TargetAggregateIdentifier
    private UUID messageId;
    private String recipient;
    private String subject;
    private String body;
    private UUID emailUserId;
}
