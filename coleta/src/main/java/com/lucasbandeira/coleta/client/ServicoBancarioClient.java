package com.lucasbandeira.coleta.client;

import com.lucasbandeira.coleta.model.Coleta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {

    public String solicitarPagamento( Coleta coleta){
        log.info("solicitando o pagamento para a coleta: {}",coleta.toString());
        return UUID.randomUUID().toString();
    }
}
