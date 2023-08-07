package com.restapi.restapi.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.security.GeneralSecurityException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handlerAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<ErrorResponse> handlerUserNotFoundExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityExceptions.class)
    public final ResponseEntity<ErrorResponse> handlerSecurityExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new GlobalErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error", errors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public GlobalErrorResponse handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("token", "Token has expired. Please login and try again");
        return new GlobalErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", errors);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(GeneralSecurityException.class)
    public GlobalErrorResponse handleForbidden(GeneralSecurityException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("message", "Access denied. You do not have permission to perform this operation.");
        return new GlobalErrorResponse(HttpStatus.FORBIDDEN.value(), "Forbidden", errors);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class GlobalErrorResponse {
        private int status;
        private String message;
        private Map<String, String> errors;
    }


}
