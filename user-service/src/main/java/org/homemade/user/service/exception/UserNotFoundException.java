package org.homemade.user.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {


    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
