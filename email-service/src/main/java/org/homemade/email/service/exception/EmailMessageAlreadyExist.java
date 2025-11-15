package org.homemade.email.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailMessageAlreadyExist extends BaseException {

    public EmailMessageAlreadyExist( String message) {
        super( message, "EMAIL_MESSAGE_ALREADY_EXIST" , HttpStatus.CONFLICT);
    }
}
