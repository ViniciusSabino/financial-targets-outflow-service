package com.financialtargets.outflow.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EssentialOutflowException extends Exception {
    public EssentialOutflowException(String message) {
        super(message);
    }
}