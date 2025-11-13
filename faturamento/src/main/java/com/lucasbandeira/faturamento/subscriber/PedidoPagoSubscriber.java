package com.lucasbandeira.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.faturamento.mapper.ColetaMapper;
import com.lucasbandeira.faturamento.model.Coleta;
import com.lucasbandeira.faturamento.service.GeradorNotaFiscalService;
import com.lucasbandeira.faturamento.subscriber.representation.DetalheColetaRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService service;
    private final ColetaMapper mapper;

    @KafkaListener(groupId = "healthflow-faturamento",
            topics = "${healthflow.config.kafka.topics.pedidos-pagos}")
    public void listen(String json){
        try {
            log.info("recebendo pedido para o faturamento: {}",json);
            var representation = objectMapper.readValue(json, DetalheColetaRepresentation.class);
            Coleta coleta = mapper.toEntity(representation);
            service.gerarNotaFiscal(coleta);
        } catch (Exception e) {
            log.error("Erro ao consumir o topico de pedidos pagos");
        }
    }
}
