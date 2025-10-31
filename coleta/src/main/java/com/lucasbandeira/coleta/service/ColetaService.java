package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.client.ServicoBancarioClient;
import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.ItemColeta;
import com.lucasbandeira.coleta.repository.ColetaRepository;
import com.lucasbandeira.coleta.repository.ItemColetaRepository;
import com.lucasbandeira.coleta.validator.ColetaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaRepository coletaRepository;
    private final ColetaValidator validator;
    private final ServicoBancarioClient servicoBancario;

    @Transactional
    public Coleta criarColeta( Coleta coleta ) {
        validator.validar(coleta);
        realizarPersistencia(coleta);
        enviarSolicitacaoPagamento(coleta);
        return coleta;
    }

    private void enviarSolicitacaoPagamento( Coleta coleta ) {
        String chavePagamento = servicoBancario.solicitarPagamento(coleta);
        coleta.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia( Coleta coleta ) {
        coletaRepository.save(coleta);
        List <ItemColeta> itemColetas = itemColetaRepository.saveAll(coleta.getItens());
        System.out.println("lista itens: " + itemColetas);
    }

    
}
