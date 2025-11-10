package org.homemade.user.service.service;

import jakarta.transaction.Transactional;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserDeletedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.exception.UserAlreadyExistException;
import org.homemade.user.service.exception.UserNotFoundException;
import org.homemade.user.service.mapper.UserMapper;
import org.homemade.user.service.mapper.UserQueryMapper;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.query.FindUserQuery;
import org.homemade.user.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryMapper userQueryMapper;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserQueryMapper userQueryMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userQueryMapper = userQueryMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public void createUser(UserCreatedEvent event) {

        System.out.println("db try to save user: " + event.getEmail());
        checkUserExistByEmailAndUsername(event.getEmail(), event.getUsername());
        User userToSave = userQueryMapper.mapUserCreatedEventToUser(event);
        userRepository.save(userToSave);

    }

    @Transactional
    public void updateUser(UserUpdatedEvent event) {

        checkUserExistByEmailAndUsername(event.getEmail(), event.getUsername());
        User userToUpdate = userQueryMapper.mapUserUpdateEvent(event);
        userRepository.save(userToUpdate);
    }


    @Transactional
    public void deleteUser(UserDeletedEvent event) {
        checkUserExistById(event.getUserId());
        userRepository.deleteById(event.getUserId());
    }

    public UserResponseDTO getUserByEmail(FindUserQuery findUserQuery) {
        checkUserExistByEmail(findUserQuery.getEmail());
        User user = userRepository.findByEmail(findUserQuery.getEmail()).get();
        return userMapper.mapUserToUserResponseDTO(user);
    }

    private void checkUserExistByEmail(String email) {
        userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public void checkUserExistById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }


    @Transactional
    public void checkUserExistByEmailAndUsername(String email, String username) {
        if (userRepository.existsByEmailAndUsername(email, username)) {
            throw new UserAlreadyExistException("User already exist whit email: " + email + " and username: " + username);
        }

    }


    public List<User> findAllUsers() {
        System.out.println("db is called to get all users");
        return userRepository.findAll();
    }
}
