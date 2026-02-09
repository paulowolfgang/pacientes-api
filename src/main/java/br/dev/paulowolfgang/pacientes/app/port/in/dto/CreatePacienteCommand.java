package br.dev.paulowolfgang.pacientes.app.port.in.dto;

import java.time.LocalDate;

public record CreatePacienteCommand(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        EnderecoCommand endereco
){}
