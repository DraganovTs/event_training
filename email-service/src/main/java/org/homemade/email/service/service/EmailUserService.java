package org.homemade.email.service.service;

import jakarta.transaction.Transactional;
import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.common.event.choreography.event.EmailUserEmailUpdatedEvent;
import org.homemade.common.event.orchestration.event.UserEmailUpdatedEvent;
import org.homemade.email.service.exception.EmailUserAlreadyExist;
import org.homemade.email.service.exception.EmailUserNotFoundException;
import org.homemade.email.service.mapper.EmailUserMapper;
import org.homemade.email.service.model.entity.EmailUser;
import org.homemade.email.service.repository.EmailUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailUserService {

    private final EmailUserRepository emailUserRepository;
    private final EmailUserMapper emailUserMapper;

    public EmailUserService(EmailUserRepository emailUserRepository, EmailUserMapper emailUserMapper) {
        this.emailUserRepository = emailUserRepository;
        this.emailUserMapper = emailUserMapper;
    }

    public void createUserFromUserDataCreatedEvent(UserDataCreatedEvent event) {
        checkIfEmailUserExistByNameAndEmail(event.getUsername(), event.getEmail());
        EmailUser emailUserToSave = emailUserMapper.mapUserDataCreatedEventToEmailUser(event);
        emailUserRepository.save(emailUserToSave);

    }


    public void updateEmailUserEmail(UserEmailUpdatedEvent event) {
        checkIfEmailUserExistById(event.getUserId());
        EmailUser emailUserToUpdate = emailUserRepository.findById(event.getUserId()).get();
        emailUserToUpdate.setOwnerEmail(event.getNewEmail());
        emailUserRepository.save(emailUserToUpdate);
    }


    private void checkIfEmailUserExistById(UUID userId) {
        if (!emailUserRepository.existsById(userId)){
            throw new EmailUserNotFoundException("Email user not exist whit id " + userId);
        }
    }

    private void checkIfEmailUserExistByNameAndEmail(String username, String email) {
        Optional<EmailUser> optionalEmailUser = emailUserRepository.findByOwnerNameAndOwnerEmail(username, email);
        if (optionalEmailUser.isPresent()) {
            throw new EmailUserAlreadyExist("Email user already exist whit username: " + username +
                    "and email: " + email);
        }
    }

}
