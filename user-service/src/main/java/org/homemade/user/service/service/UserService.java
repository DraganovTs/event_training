package org.homemade.user.service.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.homemade.user.service.exception.UserAlreadyExistException;
import org.homemade.user.service.exception.UserNotFoundException;
import org.homemade.user.service.mapper.UserMapper;
import org.homemade.user.service.model.dto.UserRequestDTO;
import org.homemade.common.model.dto.UserResponseDTO;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public UserResponseDTO createUser(@Valid UserRequestDTO request) {

        checkUserExist(request.getEmail(), request.getUsername());

        User userToSave = mapper.maoUserRequestDTOToUser(request);

        User savedUser = userRepository.save(userToSave);

        UserResponseDTO responseDTO = mapper.mapUserToUserResponseDTO(savedUser);

        return responseDTO;
    }


    public UserResponseDTO getUserById(UUID userId) {

        User foundedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found whit Id: " + userId));

        return mapper.mapUserToUserResponseDTO(foundedUser);
    }


    @Transactional
    public void checkUserExist(String email, String username) {
        if (userRepository.existsByEmailAndUsername(email, username)) {
            throw new UserAlreadyExistException("User already exist whit email: " + email + " and username: " + username);
        }

    }
}
