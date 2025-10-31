package com.lucasbandeira.coleta.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemColetaDTO {
    private Long codigoProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
}
