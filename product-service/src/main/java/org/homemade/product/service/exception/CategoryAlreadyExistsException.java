package org.homemade.product.service.exception;

import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends BaseException{

    public CategoryAlreadyExistsException(String message) {
        super(message, "CATEGORY_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
