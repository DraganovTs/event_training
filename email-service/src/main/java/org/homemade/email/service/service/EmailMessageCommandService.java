package org.homemade.email.service.service;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.homemade.email.service.command.CreateEmailMessageCommand;
import org.homemade.email.service.command.DeleteEmailMessageCommand;
import org.homemade.email.service.command.UpdateEmailMessageCommand;
import org.homemade.email.service.mapper.EmailMessageCommandMapper;
import org.homemade.email.service.model.dto.EmailRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailMessageCommandService {

    private final CommandGateway commandGateway;
    private final EmailMessageCommandMapper emailMessageCommandMapper;

    public EmailMessageCommandService(CommandGateway commandGateway, EmailMessageCommandMapper emailMessageCommandMapper) {
        this.commandGateway = commandGateway;
        this.emailMessageCommandMapper = emailMessageCommandMapper;
    }


    public void createEmailMessage(@Valid EmailRequestDTO request) {
        CreateEmailMessageCommand command = emailMessageCommandMapper.mapToCreateEmailMessageCommand(request);
        commandGateway.sendAndWait(command);
    }

    public void updateEmailMessage(@Valid EmailRequestDTO request) {
        UpdateEmailMessageCommand command = emailMessageCommandMapper.mapToUpdateEmailMessageCommand(request);
        commandGateway.sendAndWait(command);

    }

    public void deleteEmailMessage(UUID emailMessageId) {
        DeleteEmailMessageCommand command = DeleteEmailMessageCommand.builder()
                .messageId(emailMessageId)
                .build();
        commandGateway.sendAndWait(command);

    }
}
