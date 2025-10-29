package org.homemade.product.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProductExceptionHandler {


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

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseException(BaseException ex, WebRequest request) {
        return buildErrorResponse(ex, request, ex.getHttpStatus(), ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex, WebRequest request) {
        return buildErrorResponse(request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(request,
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                errorMessage);
    }

    protected ResponseEntity<ErrorResponseDTO> buildErrorResponse(WebRequest request,
                                                                  HttpStatus status,
                                                                  String errorCode,
                                                                  String message) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .status(status)
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, status);
    }

    protected ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception exception,
                                                                  WebRequest request,
                                                                  HttpStatus status,
                                                                  String errorCode) {
        return buildErrorResponse(request, status, errorCode, exception.getMessage());
    }
}
