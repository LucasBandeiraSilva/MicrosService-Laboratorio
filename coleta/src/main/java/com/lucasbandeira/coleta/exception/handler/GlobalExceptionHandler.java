package com.lucasbandeira.coleta.exception.handler;

import com.lucasbandeira.coleta.exception.ClienteNaoEncontradoException;
import com.lucasbandeira.coleta.exception.ErroResposta;
import com.lucasbandeira.coleta.exception.ProdutoNaoEncontradoException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErroResposta>handleClienteNaoEncontradoException( ClienteNaoEncontradoException e){
        var erroResposta = new ErroResposta(e.getMensagem(), e.getCampo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta>handleProdutoNaoEncontradoException( ProdutoNaoEncontradoException e){
        var erroResposta = new ErroResposta(e.getMensagem(), e.getCampo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
    }


}
