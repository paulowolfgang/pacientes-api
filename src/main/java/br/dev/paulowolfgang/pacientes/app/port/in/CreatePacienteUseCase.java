package br.dev.paulowolfgang.pacientes.app.port.in;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.CreatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;

public interface CreatePacienteUseCase
{
    PacienteResult execute(CreatePacienteCommand command);
}
