package org.homemade.user.service.exception;

import org.homemade.common.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.CONFLICT, "USER_ALREADY_EXIST");
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
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
