package org.homemade.email.service.service;

import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageDeletedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.query.FindAllEmailMessages;
import org.homemade.email.service.query.FindEmailMessageQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {


    public void createEmailMessage(EmailMessageCreatedEvent event) {

    }

    public void updateEmailMessage(EmailMessageUpdatedEvent event) {

    }

    public void deleteEmailMessage(EmailMessageDeletedEvent event) {

    }

    public EmailMessage getEmailMessageByRecipientAndSubject(FindEmailMessageQuery findEmailMessageQuery) {
        return null;
    }

    public List<EmailMessage> findAllEmails(FindAllEmailMessages findAllEmailMessages) {
        return null;
    }
}
