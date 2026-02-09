package br.dev.paulowolfgang.pacientes.infra.db.mapper;

import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Email;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Telefone;
import br.dev.paulowolfgang.pacientes.infra.db.jpa.PacienteJpaEntity;

public final class PacientePersistenceMapper
{
    private PacientePersistenceMapper(){}

    public static PacienteJpaEntity toEntity(Paciente paciente)
    {
        PacienteJpaEntity e = new PacienteJpaEntity();

        e.setId(paciente.getId());
        e.setNome(paciente.getNome());
        e.setCpf(paciente.getCpf().value());
        e.setDataNascimento(paciente.getDataNascimento());
        e.setEmail(paciente.getEmail() != null ? paciente.getEmail().value() : null);
        e.setTelefone(paciente.getTelefone() != null ? paciente.getTelefone().value() : null);

        if(paciente.getEndereco() != null)
        {
            Endereco end = paciente.getEndereco();

            e.setLogradouro(end.logradouro());
            e.setNumero(end.numero());
            e.setComplemento(end.complemento());
            e.setBairro(end.bairro());
            e.setCidade(end.cidade());
            e.setUf(end.uf());
            e.setCep(end.cep());
        }

        e.setAtivo(paciente.isAtivo());

        return e;
    }

    public static Paciente toDomain(PacienteJpaEntity e)
    {
        Endereco endereco = null;

        boolean hasEndereco =
                e.getLogradouro() != null || e.getNumero() != null || e.getComplemento() != null ||
                        e.getBairro() != null || e.getCidade() != null || e.getUf() != null || e.getCep() != null;

        if (hasEndereco)
        {
            endereco = new Endereco(
                    e.getLogradouro(),
                    e.getNumero(),
                    e.getComplemento(),
                    e.getBairro(),
                    e.getCidade(),
                    e.getUf(),
                    e.getCep()
            );
        }

        return Paciente.reconstituir(
                e.getId(),
                e.getNome(),
                new Cpf(e.getCpf()),
                e.getDataNascimento(),
                e.getEmail() != null ? new Email(e.getEmail()) : null,
                e.getTelefone() != null ? new Telefone(e.getTelefone()) : null,
                endereco,
                e.isAtivo()
        );
    }
}
