package com.lucasbandeira.coleta.service;

import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.repository.ColetaRepository;
import com.lucasbandeira.coleta.repository.ItemColetaRepository;
import com.lucasbandeira.coleta.validator.ColetaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ColetaService {

    private final ItemColetaRepository itemColetaRepository;
    private final ColetaRepository coletaRepository;
    private final ColetaValidator validator;

    public Coleta criarColeta( Coleta coleta ) {
        coletaRepository.save(coleta);
        itemColetaRepository.saveAll(coleta.getItens());
        return coleta;
    }
}
