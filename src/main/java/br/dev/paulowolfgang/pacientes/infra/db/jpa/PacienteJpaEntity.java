package br.dev.paulowolfgang.pacientes.infra.db.jpa;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "PACIENTE",
        indexes = {
                @Index(name = "IDX_PACIENTE_NOME", columnList = "NOME")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_PACIENTE_CPF", columnNames = "CPF"),
                @UniqueConstraint(name = "UK_PACIENTE_EMAIL", columnNames = "EMAIL")
        }
)
public class PacienteJpaEntity
{
    @Id
    @Column(name = "ID", nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "NOME", nullable = false, length = 150)
    private String nome;

    @Column(name = "CPF", nullable = false, length = 11)
    private String cpf;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "TELEFONE", length = 15)
    private String telefone;

    @Column(name = "LOGRADOURO", length = 250)
    private String logradouro;

    @Column(name = "NUMERO", length = 20)
    private String numero;

    @Column(name = "COMPLEMENTO", length = 100)
    private String complemento;

    @Column(name = "BAIRRO", length = 100)
    private String bairro;

    @Column(name = "CIDADE", length = 100)
    private String cidade;

    @Column(name = "UF", length = 2)
    private String uf;

    @Column(name = "CEP", length = 8)
    private String cep;

    @Column(name = "ATIVO", nullable = false)
    private boolean ativo;

    @Column(name = "CRIADO_EM", nullable = false)
    private Instant criadoEm;

    @Column(name = "ATUALIZADO_EM")
    private Instant atualizadoEm;

    @PrePersist
    void prePersist()
    {
        if (criadoEm == null) criadoEm = Instant.now();
    }

    @PreUpdate
    void preUpdate()
    {
        atualizadoEm = Instant.now();
    }

    public PacienteJpaEntity(){}

    public UUID getId(){return id;}
    public void setId(UUID id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getCpf(){return cpf;}
    public void setCpf(String cpf){this.cpf = cpf;}

    public LocalDate getDataNascimento(){return dataNascimento;}
    public void setDataNascimento(LocalDate dataNascimento){this.dataNascimento = dataNascimento;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getTelefone(){return telefone;}
    public void setTelefone(String telefone){this.telefone = telefone;}

    public String getLogradouro(){return logradouro;}
    public void setLogradouro(String logradouro){this.logradouro = logradouro;}

    public String getNumero(){return numero;}
    public void setNumero(String numero){this.numero = numero;}

    public String getComplemento(){return complemento;}
    public void setComplemento(String complemento){this.complemento = complemento;}

    public String getBairro(){return bairro;}
    public void setBairro(String bairro){this.bairro = bairro;}

    public String getCidade(){return cidade;}
    public void setCidade(String cidade){this.cidade = cidade;}

    public String getUf(){return uf;}
    public void setUf(String uf){this.uf = uf;}

    public String getCep(){return cep;}
    public void setCep(String cep){this.cep = cep;}

    public boolean isAtivo(){return ativo;}
    public void setAtivo(boolean ativo){this.ativo = ativo;}

    public Instant getCriadoEm(){return criadoEm;}
    public void setCriadoEm(Instant criadoEm){this.criadoEm = criadoEm;}

    public Instant getAtualizadoEm(){return atualizadoEm;}
    public void setAtualizadoEm(Instant atualizadoEm){this.atualizadoEm = atualizadoEm;}
}
