package org.homemade.product.service.exception;

import org.homemade.common.exception.BaseExceptionHandler;
import org.homemade.common.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



@ControllerAdvice
public class ProductExceptionHandler extends BaseExceptionHandler {


    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleCategoryNotFoundException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND");
    }

    @ExceptionHandler(value = {OwnerNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleOwnerNotFoundException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "OWNER_NOT_FOUND");
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex,
                                                                                 WebRequest request) {
        return buildErrorResponse(ex, request, ex.getHttpStatus(), ex.getErrorCode());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductAlreadyExistsException(ProductAlreadyExistsException ex,
                                                                                WebRequest request) {
        return buildErrorResponse(ex, request, ex.getHttpStatus(), ex.getErrorCode());
    }

    @ExceptionHandler(OwnerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleOwnerAlreadyExistsException(OwnerAlreadyExistsException ex,
                                                                              WebRequest request) {
        return buildErrorResponse(ex, request, ex.getHttpStatus(), ex.getErrorCode());
    }


}
