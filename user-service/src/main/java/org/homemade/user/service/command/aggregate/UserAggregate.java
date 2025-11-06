package org.homemade.user.service.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.homemade.user.service.command.CreateUserCommand;
import org.homemade.user.service.command.DeleteUserCommand;
import org.homemade.user.service.command.UpdateUserCommand;
import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserDeletedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.exception.UserAlreadyExistException;
import org.homemade.user.service.model.entity.Address;
import org.homemade.user.service.model.entity.User;
import org.homemade.user.service.model.enums.AccountStatus;
import org.homemade.user.service.repository.UserRepository;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;
    private AccountStatus accountStatus;
    private List<UUID> ownedProductIds;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(CreateUserCommand command, UserRepository userRepository) {
        Optional<User> optionalUser = userRepository.findByEmail(command.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException("user already exist with email: " + command.getEmail());
        }

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        BeanUtils.copyProperties(command, userCreatedEvent);
        AggregateLifecycle.apply(userCreatedEvent);


    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        this.userId = userCreatedEvent.getUserId();
        this.username = userCreatedEvent.getUsername();
        this.email = userCreatedEvent.getEmail();
        this.firstName = userCreatedEvent.getFirstName();
        this.lastName = userCreatedEvent.getLastName();
        this.phone = userCreatedEvent.getPhone();
        this.address = userCreatedEvent.getAddress();
        this.accountStatus = userCreatedEvent.getAccountStatus();
        this.ownedProductIds = userCreatedEvent.getOwnedProductIds();
    }

    @CommandHandler
    public void handle(UpdateUserCommand updateUserCommand) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent();
        BeanUtils.copyProperties(updateUserCommand, userUpdatedEvent);
        AggregateLifecycle.apply(userUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        this.userId = userUpdatedEvent.getUserId();
        this.username = userUpdatedEvent.getUsername();
        this.email = userUpdatedEvent.getEmail();
    }


    @CommandHandler
    public void handle(DeleteUserCommand deleteUserCommand) {
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent();
        BeanUtils.copyProperties(deleteUserCommand, userDeletedEvent);
        AggregateLifecycle.apply(userDeletedEvent);
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        this.userId = userDeletedEvent.getUserId();
    }

}
