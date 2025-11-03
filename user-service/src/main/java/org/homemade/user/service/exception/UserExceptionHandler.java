package org.homemade.user.service.exception;

import org.homemade.common.exception.BaseExceptionHandler;
import org.homemade.common.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class UserExceptionHandler extends BaseExceptionHandler {


    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.CONFLICT, "USER_ALREADY_EXIST");
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
    }



}
