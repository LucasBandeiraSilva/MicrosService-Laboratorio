package com.lucasbandeira.produtos.exception.handler;

import com.lucasbandeira.produtos.exception.ErroCampo;
import com.lucasbandeira.produtos.exception.ErroResposta;
import com.lucasbandeira.produtos.exception.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity <ErroResposta> handleUsuarioNaoEncontradoException( UsuarioNaoEncontradoException e ) {
        ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErroResposta> handleMethodArgumentNotValidException( MethodArgumentNotValidException e ) {
        List <FieldError> fieldErrors = e.getFieldErrors();
        List <ErroCampo> erroCampos = fieldErrors.stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        ErroResposta erroResposta = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de Validação",erroCampos);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResposta);
    }
}
