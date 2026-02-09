package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.in.UpdatePacienteUseCase;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.UpdatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.app.usecase.mapper.PacienteResultMapper;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Email;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Telefone;

import java.util.Objects;
import java.util.UUID;

public class UpdatePacienteService implements UpdatePacienteUseCase
{
    private final PacienteRepositoryPort repository;

    public UpdatePacienteService(PacienteRepositoryPort repository)
    {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public PacienteResult execute(UUID id, UpdatePacienteCommand command)
    {
        Paciente paciente = repository
                .findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));

        Email email = command.email() != null ? new Email(command.email()) : null;
        Telefone telefone = command.telefone() != null ? new Telefone(command.telefone()) : null;
        Endereco endereco = toEndereco(command.endereco());

        paciente.atualizar(
                command.nome(),
                command.dataNascimento(),
                email,
                telefone,
                endereco
        );

        if(command.ativo() != null)
        {
            if(command.ativo()) paciente.ativar();
            else paciente.desativar();
        }

        Paciente saved = repository.save(paciente);

        return PacienteResultMapper.toResult(saved);
    }

    private Endereco toEndereco(EnderecoCommand e)
    {
        if(e == null) return null;

        return new Endereco(
                e.logradouro(),
                e.numero(),
                e.complemento(),
                e.bairro(),
                e.cidade(),
                e.uf(),
                e.cep() != null ? e.cep().replaceAll("\\D", "") : null
        );
    }
}
