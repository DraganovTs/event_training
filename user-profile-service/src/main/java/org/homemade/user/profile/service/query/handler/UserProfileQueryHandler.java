package org.homemade.user.profile.service.query.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.homemade.user.profile.service.service.UserProfileService;
import org.springframework.stereotype.Component;

@Component
public class UserProfileQueryHandler {

    private final UserProfileService userProfileService;


    public UserProfileQueryHandler(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @QueryHandler
    public UserProfileDTO findUserProfile(FindUserProfileQuery query){
        return userProfileService.findUserProfile(query);
    }
}
