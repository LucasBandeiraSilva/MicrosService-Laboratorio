package com.lucasbandeira.coleta.model.mapper;

import com.lucasbandeira.coleta.model.ItemColeta;
import com.lucasbandeira.coleta.model.dto.ItemColetaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemColetaMapper {

    @Mapping(source = "codigoProduto", target = "codigoProduto")
    @Mapping(source = "quantidade", target = "quantidade")
    @Mapping(source = "valorUnitario", target = "valorUnitario")
    ItemColeta toEntity( ItemColetaDTO itemColetaDTO );
}
