package com.zemoga.porfolio.external.exceptions.handlers;

import com.zemoga.porfolio.adapters.exceptions.RecordNotFoundException;
import com.zemoga.porfolio.core.exceptions.InvalidDataException;
import com.zemoga.porfolio.external.models.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {InvalidDataException.class})
    protected ResponseEntity<Object> handleInvalidData(
            InvalidDataException ex, WebRequest request) {
        final Message message = new Message(ex.getDescription());
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {RecordNotFoundException.class})
    protected ResponseEntity<Object> handleRegisterNotFound(
            RecordNotFoundException ex, WebRequest request) {
        final Message message = new Message(ex.getDescription());
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        final String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        final Message message = new Message(errorMessage);

        return new ResponseEntity<>(message, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        final Message message = new Message(ex.getMessage());
        return new ResponseEntity<>(message, status);
    }
}
