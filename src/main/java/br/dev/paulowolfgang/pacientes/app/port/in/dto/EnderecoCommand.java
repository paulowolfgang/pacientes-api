package br.dev.paulowolfgang.pacientes.app.port.in.dto;

public record EnderecoCommand(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
){}
