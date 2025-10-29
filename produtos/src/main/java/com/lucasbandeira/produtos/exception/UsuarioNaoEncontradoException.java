package com.lucasbandeira.produtos.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException( String message ) {
        super(message);
    }
}
