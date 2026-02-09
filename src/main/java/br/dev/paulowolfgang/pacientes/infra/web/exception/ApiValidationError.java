package br.dev.paulowolfgang.pacientes.infra.web.exception;

import java.time.Instant;
import java.util.List;

public record ApiValidationError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ValidationError> fieldErrors
){}
