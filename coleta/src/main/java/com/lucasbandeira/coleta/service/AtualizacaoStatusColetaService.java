package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.enums.StatusExame;
import com.lucasbandeira.coleta.repository.ColetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizacaoStatusColetaService {

    private final ColetaRepository repository;


    @Transactional
    public void atualizarStatus( Long id, StatusExame statusExame,
                                 String urlNotaFiscal, String codigoAcompanhamento ) {

        repository.findById(id).ifPresentOrElse(coleta -> {
            coleta.setStatusExame(statusExame);
            if(urlNotaFiscal != null) coleta.setUrlNotaFiscal(urlNotaFiscal);
            if (codigoAcompanhamento != null) coleta.setCodigoAcompanhamento(codigoAcompanhamento);
        }, () -> {
            throw new RuntimeException("Pedido não encontrado");
        });
        log.info("status coleta: {}", statusExame );
    }
}
