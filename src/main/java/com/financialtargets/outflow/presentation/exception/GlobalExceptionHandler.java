package com.financialtargets.outflow.presentation.exception;

import com.financialtargets.outflow.domain.exception.EssentialOutflowException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Erros de validação dos campos (DTOs com @Valid)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error", errors);

        return ResponseEntity.badRequest().body(response);
    }

    // Exceções de ConstraintViolation (por exemplo @RequestParam/@PathVariable inválidos)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() + ": " + cv.getMessage()).collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Constraint violation", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
        List<String> errors = new ArrayList<>();

        errors.add(ex.getMessage());

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Request parameter", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EssentialOutflowException.class)
    public ResponseEntity<ErrorResponse> handleIncomeException(EssentialOutflowException ex) {
        ErrorResponse response = new ErrorResponse(ex.status.value(), ex.getMessage());

        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(response.status()).body(response);
    }

    // Exceções inesperadas (catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");

        ex.printStackTrace(); // Ou use logger

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
