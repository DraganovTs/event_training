package org.homemade.user.profile.service.service;

import org.homemade.common.event.UserDataChangedEvent;
import org.homemade.common.event.UserDataCreatedEvent;
import org.homemade.user.profile.service.exception.UserProfileAlreadyExist;
import org.homemade.user.profile.service.exception.UserProfileNotExist;
import org.homemade.user.profile.service.mapper.UserProfileMapper;
import org.homemade.user.profile.service.model.dto.UserProfileDTO;
import org.homemade.user.profile.service.model.entity.UserProfile;
import org.homemade.user.profile.service.query.FindUserProfileQuery;
import org.homemade.user.profile.service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    public void handleUserDataCreatedEvent(UserDataCreatedEvent event) {
        checkIfUserExistByUsernameAndEmail(event.getUsername(), event.getEmail());
        UserProfile userProfileToSave = userProfileMapper.mapUserDataCreatedEventToUserProfile(event);
        userProfileRepository.save(userProfileToSave);
    }

    public void handleUserDataChangedEvent(UserDataChangedEvent event) {
        checkIfUserExistByUserId(event.getUserId());
        UserProfile userProfileToSave = userProfileRepository.findById(event.getUserId()).get();
        userProfileMapper.mapUserDataChangedEventToUserProfile(userProfileToSave, event);
        userProfileRepository.save(userProfileToSave);
    }

    private void checkIfUserExistByUserId(UUID userId) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(userId);
        if (optionalUserProfile.isEmpty()) {
            throw new UserProfileNotExist("User profile not exist whit id: " + userId);
        }
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


    private void checkIfUserExistByUsernameAndEmail(String username, String email) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findUserProfileByUsername(email);
        if (optionalUserProfile.isPresent()) {
            throw new UserProfileAlreadyExist("User profile already exist whit username: " + username +
                    "email: " + email);
        }
    }


}
