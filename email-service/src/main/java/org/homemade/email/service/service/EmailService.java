package org.homemade.email.service.service;

import org.springframework.transaction.annotation.Transactional;
import org.homemade.email.service.command.event.EmailMessageCreatedEvent;
import org.homemade.email.service.command.event.EmailMessageDeletedEvent;
import org.homemade.email.service.command.event.EmailMessageUpdatedEvent;
import org.homemade.email.service.exception.EmailMessageAlreadyExist;
import org.homemade.email.service.exception.EmailMessageNotFoundException;
import org.homemade.email.service.mapper.EmailMessageQueryMapper;
import org.homemade.email.service.model.entity.EmailMessage;
import org.homemade.email.service.query.FindAllEmailMessages;
import org.homemade.email.service.query.FindEmailMessageQuery;
import org.homemade.email.service.repository.EmailMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailService {

    private final EmailMessageRepository emailMessageRepository;
    private final EmailMessageQueryMapper emailMessageQueryMapper;

    public EmailService(EmailMessageRepository emailMessageRepository, EmailMessageQueryMapper emailMessageQueryMapper) {
        this.emailMessageRepository = emailMessageRepository;
        this.emailMessageQueryMapper = emailMessageQueryMapper;
    }

    @Transactional
    public void createEmailMessage(EmailMessageCreatedEvent event) {
        System.out.println("db try to save email: " + event.getMessageId());
        checkEmailMessageExistBySubjectAndRecipient(event.getSubject(), event.getRecipient());
        EmailMessage emailMessageToSave = emailMessageQueryMapper.mapEmailMessageCreatedEventToEmailMessage(event);
        emailMessageRepository.save(emailMessageToSave);
        System.out.println("db saved");
    }


    @Transactional
    public void updateEmailMessage(EmailMessageUpdatedEvent event) {
        System.out.println("db try to update email: " + event.getMessageId());
        checkEmailMessageExistById(event.getMessageId());
        EmailMessage emailMessageToSave = emailMessageQueryMapper.mapEmailMessageUpdatedEventToEmailMessage(event);
        emailMessageRepository.save(emailMessageToSave);
        System.out.println("db updated");
    }

    @Transactional
    public void deleteEmailMessage(EmailMessageDeletedEvent event) {
        System.out.println("db try to delete email: " + event.getMessageId());
        checkEmailMessageExistById(event.getMessageId());
        emailMessageRepository.deleteById(event.getMessageId());
        System.out.println("db deleted");
    }

    public EmailMessage getEmailMessageByRecipientAndSubject(FindEmailMessageQuery findEmailMessageQuery) {
        System.out.println("db try to find email: " + findEmailMessageQuery.getRecipient() + " " + findEmailMessageQuery.getSubject());
        return emailMessageRepository.findByRecipientAndSubject(findEmailMessageQuery.getRecipient(), findEmailMessageQuery.getSubject())
                .orElseThrow(() -> new EmailMessageNotFoundException("Email not found with recipient: "
                        + findEmailMessageQuery.getRecipient() + " and subject: "
                        + findEmailMessageQuery.getSubject()));
    }

    public List<EmailMessage> findAllEmails(FindAllEmailMessages findAllEmailMessages) {
        System.out.println("db try to find all emails");
        return emailMessageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public void checkEmailMessageExistBySubjectAndRecipient(String subject, String recipient) {
        System.out.println("db try to check email: " + subject + " " + recipient);
        if (emailMessageRepository.findByRecipientAndSubject(recipient, subject).isPresent()) {
            throw new EmailMessageAlreadyExist("Email for recipient: " + recipient + " and subject: " + subject + " already exist");
        }
    }

    @Transactional(readOnly = true)
    public void checkEmailMessageExistById(UUID messageId) {
        if (emailMessageRepository.findById(messageId).isEmpty()) {
            throw new EmailMessageNotFoundException("Email not found with id: " + messageId);
        }
    }

}
