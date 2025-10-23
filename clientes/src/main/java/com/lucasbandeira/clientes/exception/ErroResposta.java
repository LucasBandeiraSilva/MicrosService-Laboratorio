package com.lucasbandeira.clientes.exception;

import java.util.List;

public record ErroResposta (int status, String mensagem, List<ErroCampo>ListErroCampo){
}
