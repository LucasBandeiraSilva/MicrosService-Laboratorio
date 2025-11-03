package com.lucasbandeira.coleta.exception;

import lombok.Getter;

@Getter
public class ProdutoNaoEncontradoException extends RuntimeException {

    private String mensagem;
    private String campo;

    public ProdutoNaoEncontradoException( String messagem, String campo )
    {
        super();
        this.mensagem = messagem;
        this.campo = campo;
    }
}
