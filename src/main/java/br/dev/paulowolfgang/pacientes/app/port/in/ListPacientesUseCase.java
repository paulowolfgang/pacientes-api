package br.dev.paulowolfgang.pacientes.app.port.in;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;

import java.util.List;

public interface ListPacientesUseCase
{
    List<PacienteResult> execute();
}
