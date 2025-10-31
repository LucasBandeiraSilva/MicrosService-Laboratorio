package com.lucasbandeira.coleta.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Table(name = "tb_itemColeta")
@Entity
@Data
public class ItemColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "codigo_coleta")
    @ManyToOne
    private Coleta coleta;

    private Long codigoProduto;

    private Integer quantidade;

    private BigDecimal valorUnitario;

}
