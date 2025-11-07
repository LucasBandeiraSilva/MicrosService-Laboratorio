package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.client.ClientesClient;
import com.lucasbandeira.coleta.client.ProdutosClient;
import com.lucasbandeira.coleta.client.ServicoBancarioClient;
import com.lucasbandeira.coleta.client.representation.ClienteRepresentation;
import com.lucasbandeira.coleta.client.representation.ProdutoRepresentation;
import com.lucasbandeira.coleta.enums.StatusExame;
import com.lucasbandeira.coleta.enums.TipoPagamento;
import com.lucasbandeira.coleta.exception.ColetaNaoEncontradaException;
import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.DadosPagamento;
import com.lucasbandeira.coleta.model.ItemColeta;
import com.lucasbandeira.coleta.repository.ColetaRepository;
import com.lucasbandeira.coleta.repository.ItemColetaRepository;
import com.lucasbandeira.coleta.validator.ColetaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaRepository coletaRepository;
    private final ColetaValidator validator;
    private final ServicoBancarioClient servicoBancario;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;


    private static void atualizarStatus( boolean sucesso, String observacoes, Coleta coleta ) {
        if (sucesso) {
            coleta.setStatusExame(StatusExame.PAGO);
        } else {
            coleta.setStatusExame(StatusExame.ERRO_PAGAMENTO);
            coleta.setObservacoes(observacoes);
        }
    }

    @Transactional
    public Coleta criarColeta( Coleta coleta ) {
        validator.validar(coleta);
        realizarPersistencia(coleta);
        enviarSolicitacaoPagamento(coleta);
        return coleta;
    }

    private void enviarSolicitacaoPagamento( Coleta coleta ) {
        String chavePagamento = servicoBancario.solicitarPagamento(coleta);
        coleta.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia( Coleta coleta ) {
        coletaRepository.save(coleta);
        List <ItemColeta> itemColetas = itemColetaRepository.saveAll(coleta.getItens());
    }

    public void atualizarStatusPagamento( Long codigoColeta, String chavePagamento, boolean sucesso, String observacoes ) {
        coletaRepository.findByIdAndChavePagamento(codigoColeta, chavePagamento).ifPresentOrElse(coleta -> {
            atualizarStatus(sucesso, observacoes, coleta);
            coletaRepository.save(coleta);
        }, () -> {
            throw new ColetaNaoEncontradaException(String.format("Coleta com o código: %d e chave %s não encontrada", codigoColeta, chavePagamento));
        });

    }

    @Transactional
    public void adicionarNovoPagamento( Long codigo, String dadosCartao, TipoPagamento tipoPagamento ) {

        coletaRepository.findById(codigo).ifPresentOrElse(coleta -> {
            DadosPagamento dadosPagamento = new DadosPagamento();
            dadosPagamento.setTipoPagamento(tipoPagamento);
            dadosPagamento.setDados(dadosCartao);

            coleta.setDadosPagamento(dadosPagamento);
            coleta.setStatusExame(StatusExame.REALIZADO);
            coleta.setObservacoes("Novo pagamento realizado, aguardando o processamento");


            String novaChavePagamento = servicoBancario.solicitarPagamento(coleta);
            coleta.setChavePagamento(novaChavePagamento);

            coletaRepository.save(coleta);
        }, () -> {
            throw new ColetaNaoEncontradaException(String.format("Coleta com o código: %d não encontrada", codigo));
        });
    }

    public Optional <Coleta> carregarDadosCompletosPedidos( Long id ) {
        Optional <Coleta> coleta = coletaRepository.findById(id);

        coleta.ifPresent(this::carregarDadosCliente);
        coleta.ifPresent(this::carregarDadosItensPedido);

        return coleta;

    }

    private void carregarDadosCliente( Coleta coleta ) {
        Long codigoCliente = coleta.getCodigoCliente();
        var response = apiClientes.obterDados(codigoCliente);
        coleta.setDadosCliente(response.getBody());
    }

    private void carregarDadosItensPedido( Coleta coleta ) {
        List<ItemColeta> itens = itemColetaRepository.findByColeta(coleta);
        coleta.setItens(itens);
        coleta.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemColeta itemColeta){
        Long codigoProduto = itemColeta.getCodigoProduto();
        var response = apiProdutos.obterDados(codigoProduto);
        String nomeProduto = response.getBody().nome();
        itemColeta.setNome(nomeProduto);
    }
}
