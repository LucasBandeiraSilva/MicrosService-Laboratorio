package com.lucasbandeira.coleta.client;

import com.lucasbandeira.coleta.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes",url = "${healthflow.coletas.clients.clientes.url}")
public interface ClientesClient {

    @GetMapping("/{id}")
    ResponseEntity<ClienteRepresentation>obterDados( @PathVariable Long id );
}
