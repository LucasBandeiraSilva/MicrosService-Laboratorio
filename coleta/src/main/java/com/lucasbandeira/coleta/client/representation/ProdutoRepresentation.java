package com.lucasbandeira.coleta.client.representation;

import java.math.BigDecimal;

public record ProdutoRepresentation(
        Long id,
        String nome,
        BigDecimal valorUnitario,
        boolean ativo) {
}
