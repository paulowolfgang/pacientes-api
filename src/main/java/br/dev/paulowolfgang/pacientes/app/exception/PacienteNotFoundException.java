package br.dev.paulowolfgang.pacientes.app.exception;

import java.util.UUID;

public class PacienteNotFoundException extends RuntimeException
{
    public PacienteNotFoundException(UUID id)
    {
        super("Paciente não encontrado para o ID = " + id);
    }
}
