package org.homemade.email.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailMessageNotFoundException extends BaseException {
    public EmailMessageNotFoundException(String message) {
        super(message, "EMAIL_MESSAGE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
