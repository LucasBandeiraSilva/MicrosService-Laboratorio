package com.lucasbandeira.coleta.repository;


import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColetaRepository extends JpaRepository<Coleta,Long> {
}
