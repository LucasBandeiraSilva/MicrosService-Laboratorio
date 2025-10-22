package com.lucasbandeira.clientes.repository;

import com.lucasbandeira.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
