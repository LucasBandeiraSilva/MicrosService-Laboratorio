package com.lucasbandeira.clientes.controller;

import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.model.dto.ClienteDTO;
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

    private static URI getUri( Cliente clienteCriado ) {
        URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteCriado.getId()).toUri();
        return uriLocation;
    }

    @PostMapping
    public ResponseEntity <Void> salvar( @Valid @RequestBody ClienteDTO clienteDTO ) {
        Cliente clienteCriado = clienteService.salvar(clienteDTO);
        URI uriLocation = getUri(clienteCriado);
        return ResponseEntity.created(uriLocation).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <ClienteDTO> obterPorId( @PathVariable Long id ) {
        ClienteDTO cliente = clienteService.obterPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deletarPorId( @PathVariable Long id ) {
        clienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <Void> atualizarDadosUsuario( @PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO ) {
        clienteService.atualizarUsuario(id, clienteDTO);
        return ResponseEntity.noContent().build();
    }

}
