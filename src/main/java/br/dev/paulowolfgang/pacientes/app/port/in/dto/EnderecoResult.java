package br.dev.paulowolfgang.pacientes.app.port.in.dto;

public record EnderecoResult(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
){}
