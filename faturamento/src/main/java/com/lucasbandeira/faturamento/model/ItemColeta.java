package com.lucasbandeira.faturamento.model;

import java.math.BigDecimal;

public record ItemColeta(Long id, String descricao, BigDecimal valorUnitario, Integer quantidade,BigDecimal total) {


}
