package com.br.senac.sp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OpcaoInvalidaException extends RuntimeException {
    public OpcaoInvalidaException(String message) {
        super(message);
    }
}
