package com.lucasbandeira.coleta.publisher;

import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.publisher.representation.DetalheColetaRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalheColetaMapper {


    @Mapping(source = "id",target = "id")
    @Mapping(source = "codigoCliente",target = "codigoCliente")
    @Mapping(source = "dadosCliente.nome",target = "nome")
    @Mapping(source = "dadosCliente.dataNascimento",target = "dataNascimento")
    @Mapping(source = "dadosCliente.cpf",target = "cpf")
    @Mapping(source = "dadosCliente.email",target = "email")
    @Mapping(source = "dadosCliente.telefone",target = "telefone")
    @Mapping(source = "dataPedido",target = "dataPedido", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "total",target = "total")
    @Mapping(source = "statusExame",target = "statusExame")
    @Mapping(source = "itens",target = "itens")
    DetalheColetaRepresentation toRepresentation( Coleta coleta );
}
