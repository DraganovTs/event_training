package org.homemade.product.service.exception;

import org.springframework.http.HttpStatus;

public class OwnerNotFoundException extends BaseException{

    public OwnerNotFoundException(String message) {
        super(message , "OWNER_NOT_FOUND" , HttpStatus.NOT_FOUND);
    }
}
