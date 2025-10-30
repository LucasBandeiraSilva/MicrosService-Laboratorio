package com.lucasbandeira.coleta.controller;

import com.lucasbandeira.coleta.model.Coleta;
import com.lucasbandeira.coleta.model.dto.ColetaDTO;
import com.lucasbandeira.coleta.model.mapper.ColetaMapper;
import com.lucasbandeira.coleta.service.ColetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coletas")
public class ColetaController {

    private final ColetaService coletaService;
    private final ColetaMapper mapper;

    private static URI uriLocation( Coleta coleta ) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(coleta.getId()).toUri();
    }

    @PostMapping
    public ResponseEntity <Void> criarColeta( @RequestBody ColetaDTO coletaDTO ) {
        var coleta = mapper.toEntity(coletaDTO);
        Coleta novaColeta = coletaService.criarColeta(coleta);
        URI location = uriLocation(coleta);
        return ResponseEntity.created(location).build();
    }
}
