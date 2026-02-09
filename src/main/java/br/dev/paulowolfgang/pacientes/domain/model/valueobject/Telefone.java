package br.dev.paulowolfgang.pacientes.domain.model.valueobject;

import br.dev.paulowolfgang.pacientes.domain.exception.DomainException;

import java.util.Objects;

public record Telefone(String value)
{
    public static final int TAMANHO_MINIMO_TELEFONE = 10;
    public static final int TAMANHO_MAXIMO_TELEFONE = 11;

    public Telefone
    {
        Objects.requireNonNull(value, "telefone");
        String digits = value.replaceAll("\\D", "");

        if (digits.length() < TAMANHO_MINIMO_TELEFONE || digits.length() > TAMANHO_MAXIMO_TELEFONE)
        {
            throw new DomainException("O campo de TELEFONE deve ter 10 ou 11 dígitos");
        }

        value = digits;
    }
}
