package org.homemade.email.service.mapper;

import jakarta.validation.Valid;
import org.homemade.email.service.command.CreateEmailMessageCommand;
import org.homemade.email.service.command.UpdateEmailMessageCommand;
import org.homemade.email.service.model.dto.EmailRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageCommandMapper {
    public CreateEmailMessageCommand mapToCreateEmailMessageCommand(@Valid EmailRequestDTO request) {
        return CreateEmailMessageCommand.builder()
                .messageId(request.getId())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .body(request.getBody())
                .emailUserId(request.getEmailUserId())
                .build();
    }

    public UpdateEmailMessageCommand mapToUpdateEmailMessageCommand(@Valid EmailRequestDTO request) {
        return UpdateEmailMessageCommand.builder()
                .messageId(request.getId())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .body(request.getBody())
                .emailUserId(request.getEmailUserId())
                .build();
    }
}
