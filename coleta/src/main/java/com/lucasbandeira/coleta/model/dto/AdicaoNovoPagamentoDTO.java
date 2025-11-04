package com.lucasbandeira.coleta.model.dto;

import com.lucasbandeira.coleta.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
}
