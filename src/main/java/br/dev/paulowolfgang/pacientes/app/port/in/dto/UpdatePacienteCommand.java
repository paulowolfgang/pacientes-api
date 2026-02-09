package br.dev.paulowolfgang.pacientes.app.port.in.dto;

import java.time.LocalDate;

public record UpdatePacienteCommand(
        String nome,
        LocalDate dataNascimento,
        String email,
        String telefone,
        EnderecoCommand endereco,
        Boolean ativo
){}
