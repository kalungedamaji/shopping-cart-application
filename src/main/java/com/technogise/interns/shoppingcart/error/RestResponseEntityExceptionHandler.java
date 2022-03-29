package com.technogise.interns.shoppingcart.error;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handleBadRequest(final ConstraintViolationException ex, final WebRequest request){

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
