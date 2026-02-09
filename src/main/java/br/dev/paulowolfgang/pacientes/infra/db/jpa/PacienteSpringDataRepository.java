package br.dev.paulowolfgang.pacientes.infra.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteSpringDataRepository extends JpaRepository<PacienteJpaEntity, UUID>
{
    boolean existsByCpf(String cpf);
}
