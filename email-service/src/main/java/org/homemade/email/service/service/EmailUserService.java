package org.homemade.email.service.service;

import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.email.service.exception.EmailUserAlreadyExist;
import org.homemade.email.service.mapper.EmailUserMapper;
import org.homemade.email.service.model.entity.EmailUser;
import org.homemade.email.service.repository.EmailUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    private void checkIfEmailUserExistByNameAndEmail(String username, String email) {
        Optional<EmailUser> optionalEmailUser = emailUserRepository.findByOwnerNameAndOwnerEmail(username, email);
        if (optionalEmailUser.isPresent()) {
            throw new EmailUserAlreadyExist("Email user already exist whit username: " + username +
                    "and email: " + email);
        }
    }
}
