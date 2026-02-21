package csd230.lab1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class RestApiExceptionAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadJson(HttpMessageNotReadableException ex) {
        return Map.of(
                "status", 400,
                "error", "Bad Request",
                "message", "Invalid or missing JSON field(s): " + ex.getMostSpecificCause().getMessage()
        );
    }
}