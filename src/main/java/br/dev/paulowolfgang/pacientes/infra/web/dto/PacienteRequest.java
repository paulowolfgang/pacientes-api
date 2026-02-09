package br.dev.paulowolfgang.pacientes.infra.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank(message = "O NOME é obrigatório.")
        @Size(max = 150, message = "O NOME deve ter no máximo 150 caracteres.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})$", message = "Formato de CPF inválido.")
        String cpf,

        @NotNull(message = "A DATA DE NASCIMENTO é obrigatória.")
        @PastOrPresent(message = "A DATA DE NASCIMENTO não pode ser uma data futura.")
        LocalDate dataNascimento,

        @Email(message = "O E-MAIL informado é inválido")
        @Size(max = 150, message = "O E-MAIL deve ter no máximo 150 caracteres.")
        String email,

        @Size(min = 10, max = 15, message = "O TELEFONE deve ter entre 10 e 15caracteres.")
        String telefone,

        @Valid
        EnderecoRequest endereco
){}
