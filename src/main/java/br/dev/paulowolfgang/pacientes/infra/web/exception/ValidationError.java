package br.dev.paulowolfgang.pacientes.infra.web.exception;

public record ValidationError(
        String field,
        String message
){}
