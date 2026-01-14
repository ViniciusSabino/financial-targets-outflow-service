package com.financialtargets.outflow.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionResponse(
        String message,
        LocalDateTime timestamp,
        List<String> errors,
        String status
) {
    public ExceptionResponse(String message, String status) {
        this(message, LocalDateTime.now(), List.of(), status);
    }

    public ExceptionResponse(String message, List<String> errors, String status) {
        this(message, LocalDateTime.now(), errors, status);
    }
}