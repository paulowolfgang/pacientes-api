package br.dev.paulowolfgang.pacientes.app.port.in;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.UpdatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;

import java.util.UUID;

public interface UpdatePacienteUseCase
{
    PacienteResult execute(UUID id, UpdatePacienteCommand command);
}
