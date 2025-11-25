package org.homemade.user.profile.service.exception;

import org.homemade.common.exception.BaseExceptionHandler;
import org.homemade.common.exception.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserProfileExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(value = {UserProfileNotExist.class})
    public ResponseEntity<ErrorResponseDTO> handleUserProfileNotExist(UserProfileNotExist exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "USER_PROFILE_NOT_FOUND");
    }

    @ExceptionHandler(value = {UserProfileAlreadyExist.class})
    public ResponseEntity<ErrorResponseDTO> handleUserProfileAlreadyExist(UserProfileAlreadyExist exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.CONFLICT, "USER_PROFILE_ALREADY_EXIST");
    }
}
