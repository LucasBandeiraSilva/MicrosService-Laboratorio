package com.lucasbandeira.faturamento.service;

import com.lucasbandeira.faturamento.model.Coleta;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NotaFiscalService {

    @Value("classpath:reports/notaFiscal.jrxml")
    private Resource notaFiscal;

    @Value("classpath:reports/logo-healthFlow.png")
    private Resource logo;

    public byte[] gerarNota( Coleta coleta ) {
        try (InputStream inputStream = notaFiscal.getInputStream()) {

            Map <String, Object> params = new HashMap <>();
            params.put("NOME", coleta.cliente().nome());
            params.put("CPF", coleta.cliente().cpf());
            params.put("EMAIL", coleta.cliente().email());
            params.put("TELEFONE", coleta.cliente().telefone());
            params.put("DATA_COLETA", coleta.dataColeta());
            params.put("TOTAL_PEDIDO", coleta.total());
            params.put("LOGO",logo.getFile().getAbsolutePath());


            var dataSource = new JRBeanCollectionDataSource(coleta.itens());
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
