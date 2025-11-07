package com.lucasbandeira.coleta.repository;


import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemColetaRepository extends JpaRepository<ItemColeta,Long> {
    List<ItemColeta> findByColeta( Coleta coleta );
}
