package com.lucasbandeira.faturamento.mapper;

import com.lucasbandeira.faturamento.model.Cliente;
import com.lucasbandeira.faturamento.model.Coleta;
import com.lucasbandeira.faturamento.model.ItemColeta;
import com.lucasbandeira.faturamento.subscriber.representation.DetalheColetaRepresentation;
import com.lucasbandeira.faturamento.subscriber.representation.DetalheItemColetaRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ColetaMapper {

    public Coleta toEntity( DetalheColetaRepresentation representation ) {
        Cliente cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.telefone(),
                representation.email()
        );
        List <ItemColeta> itens = representation.itens().stream().map(this::mapItem).toList();

        return new Coleta(representation.id(), cliente, representation.dataPedido(), representation.total(), itens);
    }

    private ItemColeta mapItem( DetalheItemColetaRepresentation representation ) {
        return new ItemColeta(
                representation.codigoProduto(),
                representation.nome(),
                representation.valorUnitario(),
                representation.quantidade(),
                representation.getTotal());
    }
}
