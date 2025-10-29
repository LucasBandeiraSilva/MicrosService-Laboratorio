package com.lucasbandeira.coleta.model.mapper;

import com.lucasbandeira.coleta.model.ItemColeta;
import com.lucasbandeira.coleta.model.dto.ItemColetaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemColetaMapper {

    ItemColeta toEntity( ItemColetaDTO itemColetaDTO );
}
