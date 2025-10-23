package com.lucasbandeira.clientes.exception.handler;

import com.lucasbandeira.clientes.exception.EntityNotFoundException;
import com.lucasbandeira.clientes.exception.ErroCampo;
import com.lucasbandeira.clientes.exception.ErroResposta;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta>handleMethodArgumentNotValidException( MethodArgumentNotValidException e ){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampoList = fieldErrors.stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ErroResposta resposta = new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validação!",erroCampoList);
        return ResponseEntity.unprocessableEntity().body(resposta);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> handleEntityNotFoundException( EntityNotFoundException e ){
        ErroResposta resposta = new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(),List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
}
