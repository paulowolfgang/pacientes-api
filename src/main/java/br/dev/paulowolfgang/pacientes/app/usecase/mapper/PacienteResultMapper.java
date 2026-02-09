package br.dev.paulowolfgang.pacientes.app.usecase.mapper;

import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoResult;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;

public final class PacienteResultMapper
{
    private PacienteResultMapper() {}

    public static PacienteResult toResult(Paciente paciente)
    {
        Endereco endereco = paciente.getEndereco();
        EnderecoResult end = null;

        if (endereco != null)
        {
            end = new EnderecoResult(
                    endereco.logradouro(),
                    endereco.numero(),
                    endereco.complemento(),
                    endereco.bairro(),
                    endereco.cidade(),
                    endereco.uf(),
                    endereco.cep()
            );
        }

        return new PacienteResult(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf().value(),
                paciente.getDataNascimento(),
                paciente.getEmail() != null ? paciente.getEmail().value() : null,
                paciente.getTelefone() != null ? paciente.getTelefone().value() : null,
                end,
                paciente.isAtivo()
        );
    }
}
