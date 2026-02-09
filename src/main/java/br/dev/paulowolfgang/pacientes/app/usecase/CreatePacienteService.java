package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.CpfAlreadyExistsException;
import br.dev.paulowolfgang.pacientes.app.port.in.CreatePacienteUseCase;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.CreatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.app.usecase.mapper.PacienteResultMapper;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.*;

import java.util.Objects;

public class CreatePacienteService implements CreatePacienteUseCase
{
    private final PacienteRepositoryPort repository;

    public CreatePacienteService(PacienteRepositoryPort repository)
    {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public PacienteResult execute(CreatePacienteCommand command)
    {
        Cpf cpf = new Cpf(command.cpf());

        if(repository.existsByCpf(cpf))
        {
            throw new CpfAlreadyExistsException(cpf.value());
        }

        Email email = command.email() != null ? new Email(command.email()) : null;
        Telefone telefone = command.telefone() != null ? new Telefone(command.telefone()) : null;
        Endereco endereco = toEndereco(command.endereco());

        Paciente paciente = Paciente.novo(
                command.nome(),
                cpf,
                command.dataNascimento(),
                email,
                telefone,
                endereco
        );

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
