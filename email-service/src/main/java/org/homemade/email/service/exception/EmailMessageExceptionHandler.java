package org.homemade.email.service.exception;

import org.homemade.common.exception.BaseExceptionHandler;
import org.homemade.common.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EmailMessageExceptionHandler extends BaseExceptionHandler {


    @ExceptionHandler(value = {EmailMessageAlreadyExist.class})
    public ResponseEntity<ErrorResponseDTO> handleEmailMessageAlreadyExist(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.CONFLICT, "EMAIL_MESSAGE_ALREADY_EXIST");
    }
}
