package com.lucasbandeira.coleta.publisher.representation;

import com.lucasbandeira.coleta.enums.StatusExame;

import java.math.BigDecimal;
import java.util.List;

public record DetalheColetaRepresentation(
        Long id,
        Long codigoCliente,
        String nome,
        String dataNascimento,
        String cpf,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusExame statusExame,
        List<DetalheItemColetaRepresentation> itens
) {
}
