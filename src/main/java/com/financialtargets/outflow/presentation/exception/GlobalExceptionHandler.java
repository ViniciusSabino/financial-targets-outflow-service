package com.financialtargets.outflow.presentation.exception;

import com.financialtargets.outflow.domain.exception.BadRequestException;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Validation error", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() + ": " + cv.getMessage()).collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Constraint violation", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<ExceptionResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
        List<String> errors = new ArrayList<>();

        errors.add(ex.getMessage());

        ExceptionResponse response = new ExceptionResponse("Request parameter", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleIncomeException(BusinessException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("An unexpected error occurred");

        ex.printStackTrace(); // Ou use logger

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
