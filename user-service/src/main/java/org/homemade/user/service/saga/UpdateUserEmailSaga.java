package org.homemade.user.service.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.homemade.common.command.RollbackEmailUserCommand;
import org.homemade.common.event.choreography.event.UserEmailUpdatedEvent;
import org.homemade.common.event.orchestration.command.RollbackUserEmailCommand;
import org.homemade.common.event.orchestration.command.UpdateEmailUserEmailCommand;
import org.homemade.common.event.orchestration.command.UpdateOwnerEmailCommand;
import org.homemade.common.event.orchestration.event.OwnerEmailUpdatedEvent;
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

    @SagaEventHandler(associationProperty = "ownerId")
    public void handle(OwnerEmailUpdatedEvent event) {
        log.info("Saga Event 2[Start]: Updated user email for userId: " + event.getOwnerId());
        UpdateEmailUserEmailCommand command = UpdateEmailUserEmailCommand.builder()
                .emailUserId(event.getOwnerId())
                .ownerEmail(event.getOwnerEmail())
                .newOwnerEmail(event.getNewOwnerEmail())
                .build();

        commandGateway.send(command, new CommandCallback<>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends UpdateEmailUserEmailCommand> commandMessage,
                                 @Nonnull CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    log.error("Saga Event 2[End]: Failed to update user email for userId: " + event.getOwnerId());
                    RollbackEmailUserCommand rollbackEmailUserCommand = RollbackEmailUserCommand.builder()
                            .emailUserId(event.getOwnerId())
                            .ownerEmail(event.getOwnerEmail())
                            .newOwnerEmail(event.getNewOwnerEmail())
                            .errorMessage(commandResultMessage.exceptionResult().getMessage())
                            .build();
                    commandGateway.sendAndWait(rollbackEmailUserCommand);
                }
            }
        });
    }
}
