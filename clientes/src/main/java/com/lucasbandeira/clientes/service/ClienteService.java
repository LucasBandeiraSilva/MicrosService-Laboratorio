package com.lucasbandeira.clientes.service;

import com.lucasbandeira.clientes.exception.EntityNotFoundException;
import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.model.dto.ClienteDTO;
import com.lucasbandeira.clientes.model.mapper.ClienteMapper;
import com.lucasbandeira.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public Cliente salvar( ClienteDTO clienteDTO ) {
        Cliente cliente = mapper.toEntity(clienteDTO);
        return repository.save(cliente);
    }

    public ClienteDTO obterPorId( Long id ) {
        return repository.findById(id).map(cliente -> mapper.toDTO(cliente))
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID: " + id + " não encontrado"));
    }

    public void deletarPorId( Long id ) {
        repository.findById(id).ifPresentOrElse(cliente -> {
            cliente.setAtivo(false);
            repository.save(cliente);
        }, () -> {
            throw new EntityNotFoundException("Cliente com ID: " + id + " não encontrado");
        });

    }

    public void atualizarUsuario( Long id, ClienteDTO dadosAtualizados ) {
        repository.findById(id)
                .ifPresentOrElse(
                        cliente -> atualizar(cliente, dadosAtualizados),
                        () -> {
                            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado");
                        }
                );
    }

    private void atualizar( Cliente cliente, ClienteDTO dadosAtualizados ) {
        cliente.setNome(dadosAtualizados.getNome());
        cliente.setCpf(dadosAtualizados.getCpf());
        cliente.setEmail(dadosAtualizados.getEmail());
        cliente.setTelefone(dadosAtualizados.getTelefone());
        repository.save(cliente);
        mapper.toDTO(cliente);
    }

}
