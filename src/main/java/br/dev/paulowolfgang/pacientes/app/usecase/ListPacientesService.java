package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.port.in.ListPacientesUseCase;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.app.usecase.mapper.PacienteResultMapper;

import java.util.List;
import java.util.Objects;

public class ListPacientesService implements ListPacientesUseCase
{
    private final PacienteRepositoryPort repository;

    public ListPacientesService(PacienteRepositoryPort repository)
    {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public List<PacienteResult> execute()
    {
        return repository
                .findAll()
                .stream()
                .map(PacienteResultMapper::toResult)
                .toList();
    }
}
