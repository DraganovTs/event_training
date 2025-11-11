package org.homemade.user.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserDeletedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.service.UserService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("user-group")
public class UserProjection {

    private final UserService userService;

    public UserProjection(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        System.out.println("Handling UserCreatedEvent for: " + event.getEmail());
        userService.createUser(event);
    }

    @EventHandler
    public void on(UserUpdatedEvent event) {
        System.out.println("Handling UserUpdateEvent for: " + event.getEmail());
        userService.updateUser(event);
    }


    @EventHandler
    public void on(UserDeletedEvent event) {
        userService.deleteUser(event);
    }
}
