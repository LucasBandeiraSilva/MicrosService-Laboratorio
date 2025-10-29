package com.lucasbandeira.coleta.model;

import com.lucasbandeira.coleta.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;
}
