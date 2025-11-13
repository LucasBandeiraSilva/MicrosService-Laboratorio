package com.lucasbandeira.faturamento.subscriber.representation;


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
        List<DetalheItemColetaRepresentation> itens
) {
}
