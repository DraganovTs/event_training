package org.homemade.user.profile.service.service;

import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.user.profile.service.exception.UserProfileNotExist;
import org.homemade.user.profile.service.mapper.UserProfileMapper;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.model.entity.UserProfile;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.homemade.user.profile.service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }


    public UserProfileDTO findUserProfile(FindUserProfileQuery query) {
        checkIfUserExistByUserName(query.getUsername());
        UserProfile userProfile = userProfileRepository.findUserProfileByUsername(query.getUsername()).get();
        return userProfileMapper.mapUserProfileToUserProfileDTO(userProfile);
    }

    private void checkIfUserExistByUserName(String username) {
        Optional<UserProfile> optionalUserProfileByUsername = userProfileRepository.findUserProfileByUsername(username);
        if (optionalUserProfileByUsername.isEmpty()) {
            throw new UserProfileNotExist("User profile not exist whit username " + username);
        }
    }

    public void handleUserDataChangedEvent(UserDataChangedEvent event) {
        UserProfile userProfile = checkIfUserExist(event.getUsername(), event.getEmail());
        UserProfile userProfileToSave = userProfileMapper.mapUserDataChangedEventToUserProfile(userProfile, event);
        userProfileRepository.save(userProfileToSave);
    }

    private UserProfile checkIfUserExist(String username, String email) {
        return userProfileRepository.findUserProfileByUsernameAndEmail(username, email)
                        .orElseGet(UserProfile::new);

    }
}
