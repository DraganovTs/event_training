package org.homemade.email.service.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.homemade.common.event.EmailDataChangedEvent;
import org.homemade.email.service.command.CreateEmailMessageCommand;
import org.homemade.email.service.command.DeleteEmailMessageCommand;
import org.homemade.email.service.command.UpdateEmailMessageCommand;
import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageDeletedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.homemade.email.service.repository.EmailMessageRepository;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Aggregate
public class EmailMessageAggregate {

    @AggregateIdentifier
    private UUID messageId;
    private String recipient;
    private String subject;
    private String body;
    private UUID emailUserId;

    public EmailMessageAggregate() {
    }

    @CommandHandler
    public EmailMessageAggregate(CreateEmailMessageCommand command, EmailMessageRepository emailMessageRepository) {
        EmailMessageCreatedEvent emailMessageCreatedEvent = new EmailMessageCreatedEvent();
        BeanUtils.copyProperties(command, emailMessageCreatedEvent);
        AggregateLifecycle.apply(emailMessageCreatedEvent);

    }

    @EventSourcingHandler
    public void on(EmailMessageCreatedEvent emailMessageCreatedEvent) {
        this.messageId = emailMessageCreatedEvent.getMessageId();
        this.recipient = emailMessageCreatedEvent.getRecipient();
        this.subject = emailMessageCreatedEvent.getSubject();
        this.body = emailMessageCreatedEvent.getBody();
        this.emailUserId = emailMessageCreatedEvent.getEmailUserId();
    }

    @CommandHandler
    public void handle(UpdateEmailMessageCommand updateEmailMessageCommand) {
        EmailMessageUpdatedEvent emailMessageUpdatedEvent = new EmailMessageUpdatedEvent();
        BeanUtils.copyProperties(updateEmailMessageCommand, emailMessageUpdatedEvent);
        EmailDataChangedEvent emailDataChangedEvent = new EmailDataChangedEvent();
        BeanUtils.copyProperties(updateEmailMessageCommand, emailDataChangedEvent);
        AggregateLifecycle.apply(emailMessageUpdatedEvent).andThen(
                () -> AggregateLifecycle.apply(emailDataChangedEvent)
        );
    }

    @EventSourcingHandler
    public void on(EmailMessageUpdatedEvent emailMessageUpdatedEvent) {
        this.messageId = emailMessageUpdatedEvent.getMessageId();
        this.recipient = emailMessageUpdatedEvent.getRecipient();
        this.subject = emailMessageUpdatedEvent.getSubject();
        this.body = emailMessageUpdatedEvent.getBody();
        this.emailUserId = emailMessageUpdatedEvent.getEmailUserId();
    }

    @CommandHandler
    public void handle(DeleteEmailMessageCommand deleteEmailMessageCommand) {
        EmailMessageDeletedEvent emailMessageDeletedEvent = new EmailMessageDeletedEvent();
        BeanUtils.copyProperties(deleteEmailMessageCommand, emailMessageDeletedEvent);
        EmailDataChangedEvent emailDataChangedEvent = new EmailDataChangedEvent();
        BeanUtils.copyProperties(deleteEmailMessageCommand, emailDataChangedEvent);
        AggregateLifecycle.apply(emailMessageDeletedEvent).andThen(
                () -> AggregateLifecycle.apply(emailDataChangedEvent)
        );
    }

    @EventSourcingHandler
    public void on(EmailMessageDeletedEvent emailMessageDeletedEvent) {
        this.messageId = emailMessageDeletedEvent.getMessageId();
    }


}
