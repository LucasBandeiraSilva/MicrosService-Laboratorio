package com.lucasbandeira.coleta.subscriber.representation;

import com.lucasbandeira.coleta.enums.StatusExame;

public record AtualizacaoStatusColetaRepresentation(Long id, StatusExame statusExame, String urlNotaFiscal, String codigoAcompanhamento) {
}
