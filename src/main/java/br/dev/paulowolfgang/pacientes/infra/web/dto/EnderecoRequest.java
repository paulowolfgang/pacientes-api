package br.dev.paulowolfgang.pacientes.infra.web.dto;

public record EnderecoRequest(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
){}
