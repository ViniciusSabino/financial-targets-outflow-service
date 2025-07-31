package com.financialtargets.outflow.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ExceptionResponse(
        String message,
        LocalDateTime timestamp,
        List<String> errors
) {
    public ExceptionResponse(String message) {
        this(message, LocalDateTime.now(), List.of());
    }

    public ExceptionResponse(String message, List<String> errors) {
        this(message, LocalDateTime.now(), errors);
    }
}