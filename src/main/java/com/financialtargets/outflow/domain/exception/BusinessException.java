package com.financialtargets.outflow.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}