package org.homemade.user.service.service;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.homemade.common.command.UpdateUserEmailCommand;
import org.homemade.common.model.dto.EmailUpdateDTO;
import org.homemade.user.service.command.CreateUserCommand;
import org.homemade.user.service.command.DeleteUserCommand;
import org.homemade.user.service.command.UpdateUserCommand;
import org.homemade.user.service.mapper.UserCommandMapper;
import org.homemade.user.service.model.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCommandService {

    private final CommandGateway commandGateway;
    private final UserCommandMapper userCommandMapper;

    public UserCommandService(CommandGateway commandGateway, UserCommandMapper userCommandMapper) {
        this.commandGateway = commandGateway;
        this.userCommandMapper = userCommandMapper;
    }

    public void createUser(@Valid UserRequestDTO request) {
        CreateUserCommand createUserCommand = userCommandMapper.mapToCreateUserCommand(request);
        commandGateway.sendAndWait(createUserCommand);
    }

    public void updateUser(@Valid UserRequestDTO request) {
        UpdateUserCommand updateUserCommand = userCommandMapper.mapToUpdateUserCommand(request);
        commandGateway.sendAndWait(updateUserCommand);
    }

    public void deleteUser(UUID userId) {
        DeleteUserCommand deleteUserCommand = DeleteUserCommand.builder()
                .userId(userId)
                .build();
        commandGateway.sendAndWait(deleteUserCommand);
    }

    public void updateUserEmail(EmailUpdateDTO emailUpdateDTO) {
        UpdateUserEmailCommand updateUserEmailCommand = UpdateUserEmailCommand.builder()
                .userId(emailUpdateDTO.getUserId())
                .email(emailUpdateDTO.getOldEmail())
                .newEmail(emailUpdateDTO.getNewEmail())
                .build();

        commandGateway.sendAndWait(updateUserEmailCommand);
    }
}
