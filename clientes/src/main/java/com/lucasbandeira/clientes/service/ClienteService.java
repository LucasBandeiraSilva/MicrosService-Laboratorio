package com.lucasbandeira.clientes.service;

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

    public Optional <Cliente> obterPorId( Long id ) {
        return repository.findById(id);
    }

    public void deletarPorId( Long id ) {
        repository.findById(id).ifPresent(cliente -> {
            cliente.setAtivo(false);
            repository.save(cliente);
        });

    }

    public void atualizarUsuario(Long id, Cliente dadosAtualizados){
        repository.findById(id)
                .ifPresentOrElse(
                        cliente -> atualizar(cliente,dadosAtualizados),
                        ()->{throw new RuntimeException("Cliente com ID " + id + " não encontrado"); }
                );
    }

    private void atualizar(Cliente cliente, Cliente dadosAtualizados) {
        cliente.setNome(dadosAtualizados.getNome());
        cliente.setEmail(dadosAtualizados.getEmail());
        cliente.setTelefone(dadosAtualizados.getTelefone());
        repository.save(cliente);
    }

}
