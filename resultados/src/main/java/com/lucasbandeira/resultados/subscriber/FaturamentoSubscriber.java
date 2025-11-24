package com.lucasbandeira.resultados.subscriber;

import com.lucasbandeira.resultados.subscriber.representation.AtualizaçãoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoSubscriber {

    private final JsonMapper jsonMapper;


    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",topics = "${healthflow.config.kafka.topics.pedidos-faturados}")
    public void listen(String json){

        log.info("recebendo pedido de exame para envio de resultados: {}",json);

        var representation = jsonMapper.readValue(json, AtualizaçãoFaturamentoRepresentation.class);

        try{

        } catch (Exception e) {
            log.error("Erro ao preparar resultado do exame: ", e);
        }
    }
}
