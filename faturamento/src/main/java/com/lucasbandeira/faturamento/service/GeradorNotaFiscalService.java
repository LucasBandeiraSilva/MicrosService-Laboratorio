package com.lucasbandeira.faturamento.service;

import com.lucasbandeira.faturamento.model.Coleta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GeradorNotaFiscalService {

    public void gerarNotaFiscal( Coleta coleta ){
        log.info("Gerada a nota fiscal para o pedido: {}",coleta.id());
    }
}
