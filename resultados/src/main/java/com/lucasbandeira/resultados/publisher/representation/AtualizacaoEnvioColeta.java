package com.lucasbandeira.resultados.publisher.representation;

import com.lucasbandeira.resultados.subscriber.representation.enums.StatusExame;

public record AtualizacaoEnvioColeta(Long id, StatusExame statusExame, String codigoAcompanhamento) {
}
