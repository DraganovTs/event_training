package org.homemade.user.profile.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserProfileNotExist extends BaseException {

    public UserProfileNotExist(String message) {
        super(message, "USER_PROFILE_NOT_EXIST", HttpStatus.NOT_FOUND);
    }
}
