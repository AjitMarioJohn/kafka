package com.debezuim.example.source.debeziumsource.exceptions.handlers;

import com.debezuim.example.source.debeziumsource.exceptions.models.ErrorDetails;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.logging.Level;

@RestControllerAdvice
@Log
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.log(Level.SEVERE, String.format("IllegalArgumentException occurred :: %s", exception.getMessage()), exception);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(InvalidContentTypeException exception) {
        log.log(Level.SEVERE, String.format("InvalidContentTypeException occurred :: %s", exception.getMessage()), exception);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDetails> handleIOException(IOException exception) {
        log.log(Level.SEVERE, String.format("IOException occurred :: %s", exception.getMessage()), exception);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return ResponseEntity.internalServerError().body(errorDetails);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException exception) {
        log.log(Level.SEVERE, String.format("NullPointerException occurred :: %s", exception.getMessage()), exception);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return ResponseEntity.internalServerError().body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception) {
        log.log(Level.SEVERE, String.format("Exception occurred :: %s", exception.getMessage()), exception);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return ResponseEntity.internalServerError().body(errorDetails);
    }
}
