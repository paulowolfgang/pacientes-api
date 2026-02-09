package br.dev.paulowolfgang.pacientes.infra.web.dto;

import java.time.LocalDate;

public record PacienteUpdateRequest(
        String nome,
        LocalDate dataNascimento,
        String email,
        String telefone,
        EnderecoRequest endereco,
        Boolean ativo
){}
