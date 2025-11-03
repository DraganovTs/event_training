package org.homemade.product.service.exception;

import org.homemade.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException(String message) {
        super(message, "PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
