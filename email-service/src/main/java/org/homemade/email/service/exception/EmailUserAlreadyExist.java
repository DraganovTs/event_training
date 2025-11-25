package org.homemade.email.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailUserAlreadyExist extends BaseException {

    public EmailUserAlreadyExist(String message) {
        super(message,"EMAIL_USER_ALREADY_EXIST", HttpStatus.CONFLICT);
    }
}
