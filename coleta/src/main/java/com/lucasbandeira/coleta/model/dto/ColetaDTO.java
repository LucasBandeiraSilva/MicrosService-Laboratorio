package com.lucasbandeira.coleta.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ColetaDTO {
    private Long codigoCliente;
    private DadosPagamentoDTO dadosPagamentoDTO;
    private List<ItemColetaDTO> itens;
}
