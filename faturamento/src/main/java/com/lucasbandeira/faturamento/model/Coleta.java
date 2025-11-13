package com.lucasbandeira.faturamento.model;

import java.math.BigDecimal;
import java.util.List;

public record Coleta(Long id, Cliente cliente, String dataColeta, BigDecimal total, List<ItemColeta> itens) {
}
