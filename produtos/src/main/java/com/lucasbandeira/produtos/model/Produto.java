package com.lucasbandeira.produtos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal valorUnitario;

    private boolean ativo;

    @PrePersist
    public void prePersist() {
        this.ativo = true;
    }
}
