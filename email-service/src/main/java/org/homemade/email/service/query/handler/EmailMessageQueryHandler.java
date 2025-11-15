package org.homemade.email.service.query.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.query.FindAllEmailMessages;
import org.homemade.email.service.query.FindEmailMessageQuery;
import org.homemade.email.service.service.EmailService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailMessageQueryHandler {

    private final EmailService emailService;

    public EmailMessageQueryHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @QueryHandler
    public EmailMessage findEmailMessage(FindEmailMessageQuery findEmailMessageQuery){
        return emailService.getEmailMessageByRecipientAndSubject(findEmailMessageQuery);
    }

    @QueryHandler
    public List<EmailMessage> findAllEmailMessages(FindAllEmailMessages findAllEmailMessages){
        return emailService.findAllEmails(findAllEmailMessages);
    }
}
