package org.homemade.email.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.email.service.service.EmailUserService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("email-user-group")
public class EmailUserProjection {

       private final EmailUserService emailUserService;

    public EmailUserProjection(EmailUserService emailUserService) {
        this.emailUserService = emailUserService;
    }

    @EventHandler
    public void on(UserDataCreatedEvent event) {
        System.out.println("Handling UserDataCreatedEvent for: " + event.getEmail());
        System.out.println(event.toString());
        emailUserService.createUserFromUserDataCreatedEvent(event);
    }
}
