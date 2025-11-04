package com.lucasbandeira.coleta.model.dto;

public record RecebimentoCallbackPagamentoDTO(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes
) {
}
