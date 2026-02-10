package br.dev.paulowolfgang.pacientes.infra.db;

import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.infra.db.jpa.PacienteSpringDataRepository;
import br.dev.paulowolfgang.pacientes.infra.db.mapper.PacientePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PacienteRepositoryAdapter implements PacienteRepositoryPort
{
    private final PacienteSpringDataRepository repo;

    public PacienteRepositoryAdapter(PacienteSpringDataRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public Paciente save(Paciente paciente)
    {
        var entity = repo
                .findById(paciente.getId())
                .orElseGet(() -> {
                    var e = new br.dev.paulowolfgang.pacientes.infra.db.jpa.PacienteJpaEntity();
                    e.setId(paciente.getId());
                    return e;
                });

        PacientePersistenceMapper.updateEntityFromDomain(entity, paciente);

        var saved = repo.save(entity);

        return PacientePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Paciente> findById(UUID id)
    {
        return repo.findById(id).map(PacientePersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCpf(Cpf cpf)
    {
        return repo.existsByCpf(cpf.value());
    }

    @Override
    public List<Paciente> findAll()
    {
        return repo
                .findAll()
                .stream()
                .map(PacientePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id)
    {
        repo.deleteById(id);
    }
}
