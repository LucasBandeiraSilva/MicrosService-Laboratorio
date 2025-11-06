package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.client.ServicoBancarioClient;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaRepository coletaRepository;
    private final ColetaValidator validator;
    private final ServicoBancarioClient servicoBancario;

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
            throw new ColetaNaoEncontradaException(String.format("Coleta com o código: %d e chave %s não encontrada",codigoColeta,chavePagamento));
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
            throw new ColetaNaoEncontradaException(String.format("Coleta com o código: %d não encontrada",codigo));
        });
    }
}
