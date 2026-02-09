package br.dev.paulowolfgang.pacientes.app.port.in;

import java.util.UUID;

public interface DeletePacienteUseCase
{
    void execute(UUID id);
}
