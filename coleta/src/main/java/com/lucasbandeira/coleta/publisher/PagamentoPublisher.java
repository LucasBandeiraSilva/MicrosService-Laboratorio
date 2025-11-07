package com.lucasbandeira.coleta.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.coleta.model.Coleta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoPublisher {

    private final DetalheColetaMapper mapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${healthflow.config.kafka.topics.pedidos-pagos}")
    private String topico;


    public void publicar( Coleta coleta ){
        log.info("Publicando pedido pago: {}",coleta.getId());

        try{
            var representation = mapper.toRepresentation(coleta);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico,"dados",json);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar o JSON: {}",e.getMessage());
        }catch (RuntimeException e){
            log.error("Erro técnico ao publicar no tópico de pedidos pagos: {}",e.getMessage());
        }
    }
}
