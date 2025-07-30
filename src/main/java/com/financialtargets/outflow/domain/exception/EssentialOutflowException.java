package com.financialtargets.outflow.domain.exception;

import org.springframework.http.HttpStatus;

public class EssentialOutflowException extends Exception {
    public HttpStatus status;

    public EssentialOutflowException(String message, HttpStatus status) {
        super(message);

        this.status = status;
    }
}