package com.lucasbandeira.clientes.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException( String message ) {
        super(message);
    }
}
