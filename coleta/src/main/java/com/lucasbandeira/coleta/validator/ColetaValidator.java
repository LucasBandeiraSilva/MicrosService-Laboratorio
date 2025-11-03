package com.lucasbandeira.coleta.validator;

import com.lucasbandeira.coleta.client.ClientesClient;
import com.lucasbandeira.coleta.client.ProdutosClient;
import com.lucasbandeira.coleta.client.representation.ClienteRepresentation;
import com.lucasbandeira.coleta.client.representation.ProdutoRepresentation;
import com.lucasbandeira.coleta.exception.ClienteNaoEncontradoException;
import com.lucasbandeira.coleta.exception.ProdutoNaoEncontradoException;
import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ColetaValidator {

    private final ClientesClient clientesClient;
    private final ProdutosClient produtosClient;


    private static ProdutoRepresentation throwErroProduto( Long codigoProduto, String motivo ) {
        var mensagem = String.format("Produto de código: %d, %s", codigoProduto, motivo);
        throw new ProdutoNaoEncontradoException(mensagem, "codigoProduto");
    }

    private static ClienteRepresentation throwClienteNaoEncontrado( Long codigoCliente, String motivo ) {
        var mensagem = String.format("Cliente de código: %d, %s", codigoCliente, motivo);
        throw new ClienteNaoEncontradoException(mensagem, "codigoCliente");
    }


    public void validar( Coleta coleta ) {
        Long codigoCliente = coleta.getCodigoCliente();
        validarCliente(codigoCliente);
        coleta.getItens().forEach(this::validarItem);
    }

    private void validarCliente( Long codigoCliente ) {
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
        } catch (FeignException.NotFound e) {
            throwClienteNaoEncontrado(codigoCliente, "não encontrado");
        }
    }

    private void validarItem( ItemColeta itemColeta ) {
        Long codigoProduto = itemColeta.getCodigoProduto();
        ProdutoRepresentation produto = buscarProduto(codigoProduto);
        log.info("produto consultado: {}, (codigo:{})", produto.nome(), produto.id());
    }

    private ProdutoRepresentation buscarProduto( Long codigoProduto ) {
        try {
            var response = produtosClient.obterDados(codigoProduto);
            return response.getBody();
        } catch (FeignException.NotFound e) {
            return throwErroProduto(codigoProduto, "Não encontrado");
        }
    }
}
