package com.lucasbandeira.clientes.controller;

import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.model.dto.ClienteDTO;
import com.lucasbandeira.clientes.model.dto.ClienteResponseDTO;
import com.lucasbandeira.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity <ClienteDTO> salvar( @Valid @RequestBody ClienteDTO clienteDTO ) {
        ClienteDTO clienteCriado = clienteService.salvar(clienteDTO);
        URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteCriado.getId()).toUri();
        return ResponseEntity.created(uriLocation).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <ClienteResponseDTO> obterPorId( @PathVariable Long id ) {
        ClienteResponseDTO cliente = clienteService.obterPorId(id);
        System.out.println("Dados do cliente: " + cliente.toString());
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deletarPorId( @PathVariable Long id){
        clienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarDadosUsuario(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO){
        clienteService.atualizarUsuario(id,clienteDTO);
        return ResponseEntity.noContent().build();
    }

}
