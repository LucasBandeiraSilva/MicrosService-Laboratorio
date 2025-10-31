package com.lucasbandeira.coleta.model;

import com.lucasbandeira.coleta.enums.StatusExame;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_coleta")
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long codigoCliente;

    private LocalDateTime dataPedido;

    private BigDecimal total;

    private String chavePagamento;

    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusExame statusExame;

    private String urlResultado;

    @OneToMany(mappedBy = "coleta")
    private List<ItemColeta>itens;

    @Transient
    private DadosPagamento dadosPagamento;

}
