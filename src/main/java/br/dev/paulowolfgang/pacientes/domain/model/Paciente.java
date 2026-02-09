package br.dev.paulowolfgang.pacientes.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Paciente
{
    private final UUID id;
    private String nome;
    private Cpf cpf;
    private LocalDate dataNascimento;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    private boolean ativo;

    private Paciente(UUID id,
                     String nome,
                     Cpf cpf,
                     LocalDate dataNascimento,
                     Email email,
                     Telefone telefone,
                     Endereco endereco,
                     boolean ativo)
    {
        this.id = Objects.requireNonNull(id, "id");
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.ativo = ativo;
    }

    public static Paciente novo(String nome,
                                Cpf cpf,
                                LocalDate dataNascimento,
                                Email email,
                                Telefone telefone,
                                Endereco endereco)
    {
        return new Paciente(UUID.randomUUID(), nome, cpf, dataNascimento, email, telefone, endereco, true);
    }

    public static Paciente reconstituir(UUID id,
                                        String nome,
                                        Cpf cpf,
                                        LocalDate dataNascimento,
                                        Email email,
                                        Telefone telefone,
                                        Endereco endereco,
                                        boolean ativo)
    {
        return new Paciente(id, nome, cpf, dataNascimento, email, telefone, endereco, ativo);
    }

    public void atualizar(String nome,
                          LocalDate dataNascimento,
                          Email email,
                          Telefone telefone,
                          Endereco endereco)
    {
        setNome(nome);
        setDataNascimento(dataNascimento);
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public void ativar(){this.ativo = true;}
    public void desativar(){this.ativo = false;}

    private void setNome(String nome)
    {
        if (nome == null || nome.isBlank()) throw new DomainException("O campo NOME é obrigatório.");
        if (nome.length() > 150) throw new DomainException("O campo NOME deve ter no máximo 150 caracteres.");
        this.nome = nome.trim();
    }

    private void setCpf(Cpf cpf)
    {
        this.cpf = Objects.requireNonNull(cpf, "cpf");
    }

    private void setDataNascimento(LocalDate dataNascimento)
    {
        if (dataNascimento == null) throw new DomainException("O campo DATA DE NASCIMENTO é obrigatório.");
        if (dataNascimento.isAfter(LocalDate.now())) throw new DomainException("O campo DATA DE NASCIMENTO não pode ser uma data futura a atual.");
        this.dataNascimento = dataNascimento;
    }

    public UUID getId(){return id;}
    public String getNome(){return nome;}
    public Cpf getCpf(){return cpf;}
    public LocalDate getDataNascimento(){return dataNascimento;}
    public Email getEmail(){return email;}
    public Telefone getTelefone(){return telefone;}
    public Endereco getEndereco(){return endereco;}
    public boolean isAtivo(){return ativo;}
}
