package com.lucasbandeira.produtos.service;

import com.lucasbandeira.produtos.mapper.ProdutoMapper;
import com.lucasbandeira.produtos.model.Produto;
import com.lucasbandeira.produtos.model.dto.ProdutoDTO;
import com.lucasbandeira.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public Produto salvar( Produto produto ) {
        return repository.save(produto);
    }

    public Produto obterPorId( Long id ) {
        return repository.findById(id)
                .filter(Produto::isAtivo)
                .orElseThrow(() -> new RuntimeException("Produto com id: " + id + " não encontrado ou está inativo"));
    }

    public void deletarExame( Long id ) {
        repository.findById(id).ifPresentOrElse(produto -> {
            produto.setAtivo(false);
            repository.save(produto);
        },()->{throw new RuntimeException("Produto não encontrado");});
    }

    public List<ProdutoDTO>listaProdutos(){
       return repository.findAll().stream().filter(Produto::isAtivo).map(mapper::toDTO).toList();
    }

    public void atualizarExame(Long id,ProdutoDTO dadosAtualizados){
        repository.findById(id).filter(Produto::isAtivo).map(produto -> {
            atualizar(produto,dadosAtualizados);
            return repository.save(produto);
        }).orElseThrow(()-> new RuntimeException("Pruduto não encontrado ou inativo"));
    }

    private void atualizar(Produto produto, ProdutoDTO dadosAtualizados){
        produto.setNome(dadosAtualizados.getNome());
        produto.setValorUnitario(dadosAtualizados.getValorUnitario());
    }
}
