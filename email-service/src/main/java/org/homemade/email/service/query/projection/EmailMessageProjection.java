package org.homemade.email.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageDeletedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.homemade.email.service.service.EmailService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("email-group")
public class EmailMessageProjection {

    private final EmailService emailService;

    public EmailMessageProjection(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventHandler
    public void on(EmailMessageCreatedEvent event){
        System.out.println("Handling EmailMessageCreatedEvent for: " + event.getMessageId());
        emailService.createEmailMessage(event);
    }

    @EventHandler
    public void on(EmailMessageUpdatedEvent event){
        System.out.println("Handling EmailMessageUpdatedEvent for: " + event.getMessageId());
        emailService.updateEmailMessage(event);
    }

    @EventHandler
    public void on(EmailMessageDeletedEvent event){
        System.out.println("Handling EmailMessageDeletedEvent for: " + event.getMessageId());
        emailService.deleteEmailMessage(event);
    }
}
