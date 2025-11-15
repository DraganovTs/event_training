package org.homemade.email.service.service;

import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void createEmailMessage(EmailMessageCreatedEvent event) {

    }

    public void updateEmailMessage(EmailMessageUpdatedEvent event) {

    }
}
