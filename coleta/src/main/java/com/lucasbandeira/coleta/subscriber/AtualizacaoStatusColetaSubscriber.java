package com.lucasbandeira.coleta.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.coleta.service.AtualizacaoStatusColetaService;
import com.lucasbandeira.coleta.subscriber.representation.AtualizacaoStatusColetaRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtualizacaoStatusColetaSubscriber {

    private final AtualizacaoStatusColetaService service;
    private final ObjectMapper mapper;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = {
            "${healthflow.config.kafka.topics.exames-faturados}",
            "${healthflow.config.kafka.topics.exames-prontos}"
    })
    public void receberAtualizacao( String json ) {
        log.info("recebendo atualização de status: {}", json);
        try {
            var representation = mapper.readValue(json, AtualizacaoStatusColetaRepresentation.class);
            log.info("Representation: {}", representation );
            service.atualizarStatus(
                    representation.id(),
                    representation.statusExame(),
                    representation.urlNotaFiscal(),
                    representation.codigoAcompanhamento()
            );
            log.info("status coleta: {}", representation.statusExame() );
            log.info("Pedido Atualizado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao atualizar status da coleta: ", e.getMessage());
        }
    }
}
