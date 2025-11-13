package com.lucasbandeira.faturamento.subscriber.representation;

import java.math.BigDecimal;

public record DetalheItemColetaRepresentation(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario) {

    public BigDecimal getTotal(){
        return valorUnitario.multiply(BigDecimal.valueOf(this.quantidade));
    }
}
