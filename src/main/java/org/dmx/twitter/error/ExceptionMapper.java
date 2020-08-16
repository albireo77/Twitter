package org.dmx.twitter.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApplicationError> handleEntityNotFoundException(HttpServletRequest request, EntityNotFoundException e) {
        ApplicationError error = new ApplicationError(e.getMessage(), e.getError().getCode());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApplicationError> handleValidationException(HttpServletRequest request, ValidationException e) {
        ApplicationError error = new ApplicationError(e.getMessage(), e.getError().getCode());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
