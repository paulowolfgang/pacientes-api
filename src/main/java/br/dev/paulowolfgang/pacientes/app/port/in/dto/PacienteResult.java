package br.dev.paulowolfgang.pacientes.app.port.in.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResult(
        UUID id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        EnderecoResult endereco,
        boolean ativo
){}
