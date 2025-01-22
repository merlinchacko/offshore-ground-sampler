package com.fugro.ogs.infrastructure;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fugro.ogs.domain.sample.InvalidSampleInputException;


@ControllerAdvice
public class AppErrorHandler
{
    @ExceptionHandler(InvalidSampleInputException.class)
    public ResponseEntity<ErrorResponse> handleThresholdBreach(final InvalidSampleInputException ex) {
        final ErrorResponse errorResponse = new ErrorResponse("THRESHOLD_BREACH", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(final Exception ex) {
        final ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex) {
        final String message = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getField)
            .map(field -> field + ": " + Objects.requireNonNull(ex.getBindingResult().getFieldError(field)).getDefaultMessage())
            .collect(Collectors.joining(", "));

        final ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
