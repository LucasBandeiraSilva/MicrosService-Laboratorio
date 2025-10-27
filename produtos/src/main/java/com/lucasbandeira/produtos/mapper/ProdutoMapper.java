package com.lucasbandeira.produtos.mapper;

import com.lucasbandeira.produtos.model.Produto;
import com.lucasbandeira.produtos.model.dto.ProdutoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDTO toDTO ( Produto produto);
    Produto toEntity ( ProdutoDTO produto);
}
