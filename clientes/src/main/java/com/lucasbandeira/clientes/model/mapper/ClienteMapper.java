package com.lucasbandeira.clientes.model.mapper;

import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.model.dto.ClienteResponseDTO;
import com.lucasbandeira.clientes.model.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toDTO( Cliente cliente );

    Cliente toEntity( ClienteDTO clienteDTO );

    ClienteResponseDTO toResponseDTO( Cliente cliente );
}
