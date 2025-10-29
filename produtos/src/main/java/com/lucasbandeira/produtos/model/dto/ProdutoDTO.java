package com.lucasbandeira.produtos.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    @NotBlank(message = "É necessário informar o nome do exame!")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "É necessário informar o valor unitario do Exame!")
    @DecimalMin(value = "0.01",message = "O valor unitário deve ser maior do que zero")
    @DecimalMax(value = "5000.00",message = "o valor unitário deve ser menor do que R$5.000.00")
    private BigDecimal valorUnitario;
}
