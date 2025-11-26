package org.homemade.user.profile.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductInfoNotFoundException extends BaseException {
    public ProductInfoNotFoundException(String message) {
        super(message, "PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
