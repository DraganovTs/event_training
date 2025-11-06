package org.homemade.user.service.query.projection;

import org.axonframework.eventhandling.EventHandler;
import org.homemade.user.service.command.event.UserCreatedEvent;
import org.homemade.user.service.command.event.UserDeletedEvent;
import org.homemade.user.service.command.event.UserUpdatedEvent;
import org.homemade.user.service.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    private final UserService userService;

    public UserProjection(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void on(UserCreatedEvent event){
        userService.createUser(event);
    }

    @EventHandler
    public void on(UserUpdatedEvent event){
        userService.updateUser(event);
    }


    @EventHandler
    public void on(UserDeletedEvent event){
        userService.deleteUser(event);
    }
}
