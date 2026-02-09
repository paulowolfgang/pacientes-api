package br.dev.paulowolfgang.pacientes.infra.web.dto;

import java.time.LocalDate;

public record PacienteRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        EnderecoRequest endereco
){}
