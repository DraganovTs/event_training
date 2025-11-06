package org.homemade.user.service.command;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


import java.util.UUID;

@Data
@Builder
public class DeleteUserCommand {

    @TargetAggregateIdentifier
    private final UUID userId;


}
