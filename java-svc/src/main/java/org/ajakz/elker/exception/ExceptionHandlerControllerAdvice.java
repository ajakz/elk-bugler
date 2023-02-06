package org.ajakz.elker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ElkerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorMessage> handleElkerNotFoundException(ElkerNotFoundException e) {
        var errorMsg = new ErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    protected ResponseEntity<ErrorMessage> handleGenericException(Exception e) {
        var errorMsg = new ErrorMessage("Well this is embarrassing...");
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(errorMsg);
    }
}
