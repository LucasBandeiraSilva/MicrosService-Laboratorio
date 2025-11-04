package com.lucasbandeira.coleta.repository;


import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColetaRepository extends JpaRepository<Coleta,Long> {

    Optional<Coleta>findByIdAndChavePagamento(Long id, String chavePagamento);
}
