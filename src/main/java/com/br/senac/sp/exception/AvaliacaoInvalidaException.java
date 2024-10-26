package com.br.senac.sp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AvaliacaoInvalidaException extends RuntimeException {
    public AvaliacaoInvalidaException(String message) {
        super(message);
    }
}