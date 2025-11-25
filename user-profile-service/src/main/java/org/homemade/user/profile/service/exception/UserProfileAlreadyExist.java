package org.homemade.user.profile.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;


public class UserProfileAlreadyExist extends BaseException {

    public UserProfileAlreadyExist(String message) {
        super(message, "USER_PROFILE_ALREADY_EXIST", HttpStatus.CONFLICT);
    }
}
