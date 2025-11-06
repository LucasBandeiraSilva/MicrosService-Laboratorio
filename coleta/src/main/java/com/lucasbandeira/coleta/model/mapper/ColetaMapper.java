package com.lucasbandeira.coleta.model.mapper;

import com.lucasbandeira.coleta.enums.StatusExame;
import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import com.lucasbandeira.coleta.model.dto.ColetaDTO;
import com.lucasbandeira.coleta.model.dto.ItemColetaDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ColetaMapper {

    ItemColetaMapper ITEM_COLETA_MAPPER = Mappers.getMapper(ItemColetaMapper.class);


    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Coleta toEntity( ColetaDTO coletaDTO );

    @Named("mapItens")
    default List <ItemColeta> mapItens( List <ItemColetaDTO> dtos ) {
        List <ItemColeta> coletas = dtos.stream().map(ITEM_COLETA_MAPPER::toEntity).toList();
        return coletas;
    }

    @AfterMapping
    default void afterMapping( @MappingTarget Coleta coleta ) {
        coleta.setStatusExame(StatusExame.AGENDADO);
        coleta.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(coleta);

        coleta.setTotal(total);

        coleta.getItens().forEach(itemColeta -> itemColeta.setColeta(coleta));
    }

    private static BigDecimal calcularTotal( Coleta coleta ) {
        return coleta.getItens()
                .stream().map(item ->
                        item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}
