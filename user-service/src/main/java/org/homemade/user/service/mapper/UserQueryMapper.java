package org.homemade.user.service.mapper;

import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserQueryMapper {

    public User mapUserCreatedEventToUser(UserCreatedEvent event) {
        return User.builder()
                .userId(event.getUserId())
                .username(event.getUsername())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .email(event.getEmail())
                .phone(event.getPhone())
                .address(event.getAddress())
                .accountStatus(event.getAccountStatus())
                .ownedProductIds(event.getOwnedProductIds())
                .build();
    }

    public User mapUserUpdateEvent(UserUpdatedEvent event) {
        return User.builder()
                .userId(event.getUserId())
                .username(event.getUsername())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .email(event.getEmail())
                .phone(event.getPhone())
                .address(event.getAddress())
                .accountStatus(event.getAccountStatus())
                .ownedProductIds(event.getOwnedProductIds())
                .build();
    }
}
