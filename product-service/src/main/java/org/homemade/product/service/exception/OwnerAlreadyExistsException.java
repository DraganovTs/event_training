package org.homemade.product.service.exception;

import org.springframework.http.HttpStatus;

public class OwnerAlreadyExistsException extends BaseException{
    public OwnerAlreadyExistsException(String message) {
        super(message, "OWNER_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
