package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.repository.ColetaRepository;
import com.lucasbandeira.coleta.repository.ItemColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaRepository coletaRepository;

    private Coleta criarColeta(Coleta coleta){
        coletaRepository.save(coleta);
        itemColetaRepository.saveAll(coleta.getItens());
        return coleta;
    }
}
