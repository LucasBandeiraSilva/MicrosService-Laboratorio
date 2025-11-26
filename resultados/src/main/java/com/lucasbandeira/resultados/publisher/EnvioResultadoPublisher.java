package com.lucasbandeira.resultados.publisher;

import com.lucasbandeira.resultados.publisher.representation.AtualizacaoEnvioColeta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvioResultadoPublisher {

    private final JsonMapper jsonMapper;
    private final KafkaTemplate <String, String> kafkaTemplate;
    @Value("${healthflow.config.kafka.topics.pedidos-prontos}")
    private String topico;

    public void enviar( AtualizacaoEnvioColeta atualizacaoEnvioColeta ) {
        log.info("Publicando coleta enviada: {}", atualizacaoEnvioColeta.id());

        try {
            var json = jsonMapper.writeValueAsString(atualizacaoEnvioColeta);
            kafkaTemplate.send(topico, "dados", json);
            log.info("Publicando a coleta enviada {}, codigo de acompanhamento: {}", atualizacaoEnvioColeta.id(), atualizacaoEnvioColeta.codigoAcompanhamento());
        } catch (Exception e) {
            log.error("Erro ao publicar envio da coleta ", e);
        }
    }
}
