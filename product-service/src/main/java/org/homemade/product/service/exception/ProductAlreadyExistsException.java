package org.homemade.product.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends BaseException {
    public ProductAlreadyExistsException(String message) {
        super(message, "PRODUCT_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
