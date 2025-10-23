package com.lucasbandeira.clientes.service;

import com.lucasbandeira.clientes.exception.EntityNotFoundException;
import com.lucasbandeira.clientes.model.Cliente;
import com.lucasbandeira.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar( Cliente cliente ) {
        return repository.save(cliente);
    }

    public Cliente obterPorId( Long id ) {
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente com ID: "+ id + " não encontrado"));
    }

    public void deletarPorId( Long id ) {
        repository.findById(id).ifPresentOrElse(cliente -> {
            cliente.setAtivo(false);
            repository.save(cliente);
        },()-> {throw new EntityNotFoundException("Cliente com ID: "+ id + " não encontrado");
        });

    }

    public void atualizarUsuario(Long id, Cliente dadosAtualizados){
        repository.findById(id)
                .ifPresentOrElse(
                        cliente -> atualizar(cliente,dadosAtualizados),
                        ()->{throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado"); }
                );
    }

    private void atualizar(Cliente cliente, Cliente dadosAtualizados) {
        cliente.setNome(dadosAtualizados.getNome());
        cliente.setCpf(dadosAtualizados.getCpf());
        cliente.setEmail(dadosAtualizados.getEmail());
        cliente.setTelefone(dadosAtualizados.getTelefone());
        repository.save(cliente);
    }

}
