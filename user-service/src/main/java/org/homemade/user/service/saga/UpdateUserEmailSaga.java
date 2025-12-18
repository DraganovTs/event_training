package org.homemade.user.service.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.homemade.common.event.choreography.event.UserEmailUpdatedEvent;
import org.homemade.common.event.orchestration.command.RollbackUserEmailCommand;
import org.homemade.common.event.orchestration.command.UpdateOwnerEmailCommand;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
@Slf4j
public class UpdateUserEmailSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "userId")
    public void handle(UserEmailUpdatedEvent event) {
        log.info("Saga Event 1[Start]: Received UserEmailUpdatedEvent for userId: " + event.getUserId());
        UpdateOwnerEmailCommand command = UpdateOwnerEmailCommand.builder()
                .ownerId(event.getUserId())
                .ownerEmail(event.getEmail())
                .newOwnerEmail(event.getNewEmail())
                .build();

        commandGateway.send(command, new CommandCallback<>() {

            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateOwnerEmailCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    log.error("Saga Event 1[End]: Failed to update user email for userId: " + event.getUserId());
                    RollbackUserEmailCommand rollbackUserEmailCommand = RollbackUserEmailCommand.builder()
                            .userId(event.getUserId())
                            .email(event.getEmail())
                            .newEmail(event.getNewEmail())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.sendAndWait(rollbackUserEmailCommand);
                }
            }
        });
    }
}
