package org.homemade.email.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailUserNotFoundException extends BaseException {


    public EmailUserNotFoundException( String message) {
        super( message, "EMAIL_USER_NOT_FOUND_EXCEPTION" ,HttpStatus.NOT_FOUND);
    }
}
