package org.homemade.user.service.command.interceptor;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.homemade.user.service.command.CreateUserCommand;
import org.homemade.user.service.command.DeleteUserCommand;
import org.homemade.user.service.command.UpdateUserCommand;
import org.homemade.user.service.exception.UserAlreadyExistException;
import org.homemade.user.service.exception.UserNotFoundException;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class UserCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final UserRepository userRepository;

    public UserCommandInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, commandMessage) -> {

            if (CreateUserCommand.class.equals(commandMessage.getPayloadType())) {
                CreateUserCommand createUserCommand = (CreateUserCommand) commandMessage.getPayload();
                Optional<User> optionalUser = userRepository
                        .findByEmailAndUsername(createUserCommand.getEmail(), createUserCommand.getUsername());
                if (optionalUser.isPresent()) {
                    throw new UserAlreadyExistException("user already exist whit email: " + createUserCommand.getEmail()
                            + " and username: " + createUserCommand.getUsername());
                }
            } else if (UpdateUserCommand.class.equals(commandMessage.getPayloadType())) {
                UpdateUserCommand updateUserCommand = (UpdateUserCommand) commandMessage.getPayload();
                Optional<User> optionalUser = userRepository
                        .findByEmailAndUsername(updateUserCommand.getEmail(), updateUserCommand.getUsername());
                if (optionalUser.isEmpty()) {
                    throw new UserNotFoundException("user not exist whit email: " + updateUserCommand.getEmail()
                            + " and username: " + updateUserCommand.getUsername());
                }
            } else if (DeleteUserCommand.class.equals(commandMessage.getPayloadType())) {
                DeleteUserCommand deleteUserCommand = (DeleteUserCommand) commandMessage.getPayload();
                Optional<User> optionalUser = userRepository.findById(deleteUserCommand.getUserId());
                if (optionalUser.isEmpty()) {
                    throw new UserNotFoundException("user not found with id: " + deleteUserCommand.getUserId());
                }
            }

            return commandMessage;
        };
    }
}
