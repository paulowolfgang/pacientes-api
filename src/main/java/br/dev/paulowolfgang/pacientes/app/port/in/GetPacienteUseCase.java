package br.dev.paulowolfgang.pacientes.app.port.in;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;

import java.util.UUID;

public interface GetPacienteUseCase
{
    PacienteResult execute(UUID id);
}
