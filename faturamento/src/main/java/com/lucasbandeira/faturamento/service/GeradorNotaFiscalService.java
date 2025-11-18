package com.lucasbandeira.faturamento.service;

import com.lucasbandeira.faturamento.bucket.BucketFile;
import com.lucasbandeira.faturamento.bucket.BucketService;
import com.lucasbandeira.faturamento.model.Coleta;
import com.lucasbandeira.faturamento.publisher.FaturamentoPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;
    private final FaturamentoPublisher publisher;

    public void gerarNotaFiscal( Coleta coleta ) {
        log.info("Gerada a nota fiscal para o pedido: {}", coleta.id());
        try {
            byte[] byteArray = notaFiscalService.gerarNota(coleta);

            String nomeArquivo = String.format("NotaFiscal_Coleta_%d.pdf", coleta.id());

            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray), MediaType.APPLICATION_PDF, byteArray.length);

            String url = bucketService.getUrl(nomeArquivo);
            publisher.publicar(coleta,url);
            bucketService.upload(file);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }
}
