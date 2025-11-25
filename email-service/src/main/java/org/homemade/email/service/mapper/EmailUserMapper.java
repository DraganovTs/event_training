package org.homemade.email.service.mapper;

import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.email.service.model.entity.EmailUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmailUserMapper {
    public EmailUser mapUserDataCreatedEventToEmailUser(UserDataCreatedEvent event) {
        return EmailUser.builder()
                .emailUserId(event.getUserId())
                .ownerName(event.getUsername())
                .ownerEmail(event.getEmail())
                .emails(new ArrayList<>())
                .build();
    }
}
