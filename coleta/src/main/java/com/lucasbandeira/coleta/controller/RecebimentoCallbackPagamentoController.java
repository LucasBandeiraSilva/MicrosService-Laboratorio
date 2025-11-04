package com.lucasbandeira.coleta.controller;

import com.lucasbandeira.coleta.model.dto.RecebimentoCallbackPagamentoDTO;
import com.lucasbandeira.coleta.service.ColetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coletas/callback-pagamentos")
public class RecebimentoCallbackPagamentoController {

    private final ColetaService coletaService;

    @PostMapping
    public ResponseEntity <Void> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(name = "apiKey") String apiKey ) {

        coletaService.atualizarStatusPagamento(body.codigo(), body.chavePagamento(), body.status(), body.observacoes());

        return  ResponseEntity.ok().build();
    }
}
