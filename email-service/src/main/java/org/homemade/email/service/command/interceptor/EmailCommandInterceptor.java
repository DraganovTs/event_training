package org.homemade.email.service.command.interceptor;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.homemade.email.service.command.CreateEmailMessageCommand;
import org.homemade.email.service.command.DeleteEmailMessageCommand;
import org.homemade.email.service.command.UpdateEmailMessageCommand;
import org.homemade.email.service.exception.EmailMessageNotFoundException;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.repository.EmailMessageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class EmailCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final EmailMessageRepository emailMessageRepository;

    public EmailCommandInterceptor(EmailMessageRepository emailMessageRepository) {
        this.emailMessageRepository = emailMessageRepository;
    }


    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, commandMessage) -> {
            if (CreateEmailMessageCommand.class.equals(commandMessage.getPayloadType())) {
                CreateEmailMessageCommand command = (CreateEmailMessageCommand) commandMessage.getPayload();
                Optional<EmailMessage> optionalEmailMessage = emailMessageRepository.findByRecipientAndSubject(
                        command.getRecipient(), command.getSubject());
                if (optionalEmailMessage.isPresent()) {
                    throw new org.homemade.email.service.exception.EmailMessageAlreadyExist("email message already exist with recipient: " + command.getRecipient() + " and subject: " + command.getSubject());
                }
            } else if (commandMessage.getPayload().equals(UpdateEmailMessageCommand.class)) {
                UpdateEmailMessageCommand command = (UpdateEmailMessageCommand) commandMessage.getPayload();
                Optional<EmailMessage> optionalEmailMessage = emailMessageRepository.findByRecipientAndSubject(
                        command.getRecipient(), command.getSubject());
                if (optionalEmailMessage.isEmpty()) {
                    throw new EmailMessageNotFoundException("email message not found with recipient: " + command.getRecipient() + " and subject: " + command.getSubject());
                }
            } else if (commandMessage.getPayload().equals(DeleteEmailMessageCommand.class)) {
                DeleteEmailMessageCommand command = (DeleteEmailMessageCommand) commandMessage.getPayload();
                Optional<EmailMessage> optionalEmailMessage = emailMessageRepository.findById(command.getMessageId());
                if (optionalEmailMessage.isEmpty()) {
                    throw new EmailMessageNotFoundException("email message not found with id: " + command.getMessageId());
                }
            }
            return commandMessage;
        };
    }
}
