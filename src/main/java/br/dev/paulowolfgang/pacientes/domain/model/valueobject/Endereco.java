package br.dev.paulowolfgang.pacientes.domain.model.valueobject;

public record Endereco(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep
){}
