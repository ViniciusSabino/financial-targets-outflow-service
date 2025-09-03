package com.financialtargets.outflow.presentation.exception;

import com.financialtargets.outflow.domain.exception.BadRequestException;
import com.financialtargets.outflow.domain.exception.BusinessException;
import com.financialtargets.outflow.domain.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Validation error", errors);

        log.warn("Method Argument Not Valid Exception: {}", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() + ": " + cv.getMessage()).collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Constraint violation", errors);

        log.warn("Constraint Violation: {}", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<ExceptionResponse> handleMissingRequestValueException(MissingRequestValueException ex) {
        List<String> errors = new ArrayList<>();

        errors.add(ex.getMessage());

        ExceptionResponse response = new ExceptionResponse("Request parameter", errors);

        log.warn("Request Parameter: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        log.warn("Business Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        log.warn("Resource Not Found Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        log.warn("Bad Request Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());

        log.warn("Entity Not Found Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("An unexpected error occurred");

        log.error("An unexpected error occurred: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
