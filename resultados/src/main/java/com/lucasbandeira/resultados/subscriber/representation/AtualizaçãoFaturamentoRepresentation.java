package com.lucasbandeira.resultados.subscriber.representation;

import com.lucasbandeira.resultados.subscriber.representation.enums.StatusExame;

public record AtualizaçãoFaturamentoRepresentation(Long id, StatusExame statusExame, String urlNotaFiscal) {
}
