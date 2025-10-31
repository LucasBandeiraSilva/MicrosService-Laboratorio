package com.lucasbandeira.coleta.validator;

import com.lucasbandeira.coleta.client.ClientesClient;
import com.lucasbandeira.coleta.client.ProdutosClient;
import com.lucasbandeira.coleta.client.representation.ClienteRepresentation;
import com.lucasbandeira.coleta.client.representation.ProdutoRepresentation;
import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ColetaValidator {

    private final ClientesClient clientesClient;
    private final ProdutosClient produtosClient;

    public void validar( Coleta coleta ){
        Long codigoCliente = coleta.getCodigoCliente();
        validarCliente(codigoCliente);
        coleta.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente){
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("dados cliente: {} ", cliente.nome());
        } catch (FeignException.NotFound e) {
            log.error("cliente não encontrado");
        }


    }

    private void validarItem( ItemColeta itemColeta ){
        try{
            var response = produtosClient.obterDados(itemColeta.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("dados produto: {} ", produto.nome());
        } catch (FeignException.NotFound e){
            log.error("produto não encontrado");
        }
    }
}
