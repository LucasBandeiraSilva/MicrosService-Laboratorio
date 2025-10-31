package com.lucasbandeira.coleta.client.representation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ClienteRepresentation(
        Long id,
        String nome,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        String cpf,
        String telefone,
        String email) {
}
