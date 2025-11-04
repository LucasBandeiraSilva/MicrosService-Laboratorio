package com.lucasbandeira.coleta.exception;

import lombok.Getter;


public class ColetaNaoEncontradaException extends RuntimeException {

    public ColetaNaoEncontradaException( String message ) {
        super(message);
    }
}

