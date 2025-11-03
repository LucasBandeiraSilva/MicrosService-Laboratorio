package com.lucasbandeira.coleta.exception;

import lombok.Getter;

@Getter
public class ClienteNaoEncontradoException extends RuntimeException {

    private String mensagem;
    private String campo;

    public ClienteNaoEncontradoException( String messagem, String campo )
    {
        super();
        this.mensagem = messagem;
        this.campo = campo;
    }
}
