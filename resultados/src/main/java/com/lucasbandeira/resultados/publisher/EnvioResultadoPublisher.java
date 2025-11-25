package com.lucasbandeira.resultados.publisher;

import com.lucasbandeira.resultados.publisher.representation.AtualizacaoEnvioColeta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvioResultadoPublisher {

    public void enviar( AtualizacaoEnvioColeta atualizacaoEnvioColeta ){}
}
