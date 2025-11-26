package com.lucasbandeira.faturamento.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.faturamento.model.Coleta;
import com.lucasbandeira.faturamento.publisher.representation.AtualizacaoStatusColeta;
import com.lucasbandeira.faturamento.publisher.representation.StatusExame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor

public class FaturamentoPublisher {

    private final KafkaTemplate <String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Value("${healthflow.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar( Coleta coleta, String urlNotaFiscal ) {
        var representation = new AtualizacaoStatusColeta(coleta.id(), StatusExame.EM_ANALISE, urlNotaFiscal);
        log.info("enviado para o topico de pedidos pago: {}", representation);
        try {
            String json = mapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados", json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
