package com.lucasbandeira.produtos.exception;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo>erroCampoList) {
}
