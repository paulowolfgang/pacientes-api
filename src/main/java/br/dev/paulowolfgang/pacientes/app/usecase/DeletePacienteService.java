package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.in.DeletePacienteUseCase;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;

import java.util.Objects;
import java.util.UUID;

public class DeletePacienteService implements DeletePacienteUseCase
{
    private final PacienteRepositoryPort repository;

    public DeletePacienteService(PacienteRepositoryPort repository)
    {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public void execute(UUID id)
    {
        if(repository.findById(id).isEmpty())
        {
            throw new PacienteNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
