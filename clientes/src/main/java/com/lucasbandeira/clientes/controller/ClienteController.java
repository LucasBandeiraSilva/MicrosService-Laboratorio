package com.lucasbandeira.clientes.controller;

import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity <Cliente> salvar( @Valid @RequestBody Cliente cliente ) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Cliente> obterPorId( @PathVariable Long id ) {
        Cliente cliente = clienteService.obterPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deletarPorId( @PathVariable Long id){
        clienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarDadosUsuario(@PathVariable Long id, @Valid @RequestBody Cliente cliente){
        clienteService.atualizarUsuario(id,cliente);
        return ResponseEntity.noContent().build();
    }

}
