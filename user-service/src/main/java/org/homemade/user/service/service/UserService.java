package org.homemade.user.service.service;

import jakarta.transaction.Transactional;
import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserDeletedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.exception.UserAlreadyExistException;
import org.homemade.user.service.exception.UserNotFoundException;
import org.homemade.user.service.mapper.UserQueryMapper;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryMapper mapper;


    public UserService(UserRepository userRepository, UserQueryMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void createUser(UserCreatedEvent event) {

        checkUserExist(event.getEmail(), event.getUsername());

        User userToSave = mapper.mapUserCreatedEventToUser(event);
        userRepository.save(userToSave);

    }

    @Transactional
    public void updateUser(UserUpdatedEvent event) {
        checkUserExistById(event.getUserId());
        User userToUpdate = mapper.mapUserUpdateEvent(event);
        userRepository.save(userToUpdate);
    }


    @Transactional
    public void deleteUser(UserDeletedEvent event) {
        checkUserExistById(event.getUserId());
        userRepository.deleteById(event.getUserId());
    }

    @Transactional
    public void checkUserExistById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }


    @Transactional
    public void checkUserExist(String email, String username) {
        if (userRepository.existsByEmailAndUsername(email, username)) {
            throw new UserAlreadyExistException("User already exist whit email: " + email + " and username: " + username);
        }

    }


}
