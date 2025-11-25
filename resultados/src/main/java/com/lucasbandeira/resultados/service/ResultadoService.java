package com.lucasbandeira.resultados.service;

import com.lucasbandeira.resultados.publisher.EnvioResultadoPublisher;
import com.lucasbandeira.resultados.publisher.representation.AtualizacaoEnvioColeta;
import com.lucasbandeira.resultados.subscriber.representation.enums.StatusExame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class ResultadoService {

    private final EnvioResultadoPublisher publisher;

    public void enviarResultado( Long codigoColeta, String urlNotaFiscal ) {

        var representation = new AtualizacaoEnvioColeta(codigoColeta, StatusExame.PRONTO, gerarCodigoRastreio());
        publisher.enviar(representation);
    }

    private String gerarCodigoRastreio() {

        // AB123456789BR
        var random = new Random();

        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));

        int numeros = 100000000 + random.nextInt(900000000);

        return "" + letra1 + letra2 + numeros + "BR";
    }
}
