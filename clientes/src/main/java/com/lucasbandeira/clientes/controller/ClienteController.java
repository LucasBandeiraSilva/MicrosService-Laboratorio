package com.lucasbandeira.clientes.controller;

import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity <String> test() {
        return ResponseEntity.ok("Olá mundo");
    }

    @PostMapping
    public ResponseEntity <Cliente> salvar( @RequestBody Cliente cliente ) {
        Cliente clienteSalvo = clienteService.salvar(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Cliente> obterPorId( @PathVariable Long id ) {
        return clienteService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deletarPorId( @PathVariable Long id){
        clienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarDadosUsuario(@PathVariable Long id, @RequestBody Cliente cliente){
        clienteService.atualizarUsuario(id,cliente);
        return ResponseEntity.noContent().build();
    }

}
