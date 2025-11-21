package org.homemade.user.profile.service.query.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.user.profile.service.service.UserProfileService;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("user-profile-group")
public class UserProfileProjection {

    private final UserProfileService userProfileService;

    public UserProfileProjection(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @EventHandler
    public void on(UserDataChangedEvent event){
        userProfileService.handleUserDataChangedEvent(event);
    }
}
