package br.dev.paulowolfgang.pacientes.infra.web.mapper;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.CreatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.UpdatePacienteCommand;
import br.dev.paulowolfgang.pacientes.infra.web.dto.EnderecoRequest;
import br.dev.paulowolfgang.pacientes.infra.web.dto.PacienteRequest;
import br.dev.paulowolfgang.pacientes.infra.web.dto.PacienteUpdateRequest;

public final class PacienteWebMapper
{
    private PacienteWebMapper(){}

    public static CreatePacienteCommand toCreateCommand(PacienteRequest req)
    {
        return new CreatePacienteCommand(
                req.nome(),
                req.cpf(),
                req.dataNascimento(),
                req.email(),
                req.telefone(),
                toEnderecoCommand(req.endereco())
        );
    }

    public static UpdatePacienteCommand toUpdateCommand(PacienteUpdateRequest req)
    {
        return new UpdatePacienteCommand(
                req.nome(),
                req.dataNascimento(),
                req.email(),
                req.telefone(),
                toEnderecoCommand(req.endereco()),
                req.ativo()
        );
    }

    private static EnderecoCommand toEnderecoCommand(EnderecoRequest e)
    {
        if (e == null) return null;

        return new EnderecoCommand(
                e.logradouro(),
                e.numero(),
                e.complemento(),
                e.bairro(),
                e.cidade(),
                e.uf(),
                e.cep()
        );
    }
}
