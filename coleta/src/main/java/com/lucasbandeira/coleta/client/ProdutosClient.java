package com.lucasbandeira.coleta.client;

import com.lucasbandeira.coleta.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtos",url = "${healthflow.coletas.clients.produtos.url}")
public interface ProdutosClient {

    @GetMapping("/{id}")
    ResponseEntity<ProdutoRepresentation> obterDados( @PathVariable Long id );
}
