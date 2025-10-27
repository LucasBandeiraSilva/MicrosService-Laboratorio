package com.lucasbandeira.produtos.controller;

import com.lucasbandeira.produtos.mapper.ProdutoMapper;
import com.lucasbandeira.produtos.model.Produto;
import com.lucasbandeira.produtos.model.dto.ProdutoDTO;
import com.lucasbandeira.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>>listaProdutos(){
        List <ProdutoDTO> listaProdutos = service.listaProdutos();
        return ResponseEntity.ok(listaProdutos);
    }

    @PostMapping
    public ResponseEntity<Void> salvar( @RequestBody ProdutoDTO produtoDTO ){
        Produto produto = mapper.toEntity(produtoDTO);
        service.salvar(produto);
        URI location = getUri(produto.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> obterPorId(@PathVariable Long id){
        Produto produto = service.obterPorId(id);
        ProdutoDTO produtoDTO = mapper.toDTO(produto);
        return ResponseEntity.ok(produtoDTO);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarExame(@PathVariable Long id){
        service.deletarExame(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarExame(@PathVariable Long id, @RequestBody ProdutoDTO dadosAtualizados){
        service.atualizarExame(id,dadosAtualizados);
        return ResponseEntity.noContent().build();
    }


    private static URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }


}
