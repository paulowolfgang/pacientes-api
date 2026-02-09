package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.in.GetPacienteUseCase;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.app.usecase.mapper.PacienteResultMapper;

import java.util.Objects;
import java.util.UUID;

public class GetPacienteService implements GetPacienteUseCase
{
    private final PacienteRepositoryPort repository;

    public GetPacienteService(PacienteRepositoryPort repository)
    {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public PacienteResult execute(UUID id)
    {
        return repository
                .findById(id)
                .map(PacienteResultMapper::toResult)
                .orElseThrow(() -> new PacienteNotFoundException(id));
    }
}
