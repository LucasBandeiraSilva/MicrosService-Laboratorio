package com.lucasbandeira.produtos.repository;

import com.lucasbandeira.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
