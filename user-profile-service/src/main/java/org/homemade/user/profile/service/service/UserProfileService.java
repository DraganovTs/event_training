package org.homemade.user.profile.service.service;

import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.homemade.user.profile.service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }


    public UserProfileDTO findUserProfile(FindUserProfileQuery query) {
        return null;
    }
}
