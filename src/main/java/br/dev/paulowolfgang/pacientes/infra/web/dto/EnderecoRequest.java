package br.dev.paulowolfgang.pacientes.infra.web.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(
        @Size(max = 250, message = "O LOGRADOURO deve ter no máximo 250 caracteres.")
        String logradouro,

        @Size(max = 20, message = "O NÚMERO deve ter no máximo 20 caracteres.")
        String numero,

        @Size(max = 60, message = "O COMPLEMENTO deve ter no máximo 100 caracteres.")
        String complemento,

        @Size(max = 80, message = "O BAIRRO deve ter no máximo 100 caracteres.")
        String bairro,

        @Size(max = 80, message = "A CIDADE deve ter no máximo 100 caracteres.")
        String cidade,

        @Pattern(regexp = "^[A-Za-z]{2}$", message = "A UF deve conter 2 letras (ex: PA).")
        String uf,

        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP está inválido (ex: 66035-170).")
        String cep
){}
