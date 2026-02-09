package br.dev.paulowolfgang.pacientes.app.port.out;

import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface PacienteRepositoryPort
{
    Paciente save(Paciente paciente);
    Optional<Paciente> findById(UUID id);
    boolean existsByCpf(Cpf cpf);
    List<Paciente> findAll();
    void deleteById(UUID id);
}
