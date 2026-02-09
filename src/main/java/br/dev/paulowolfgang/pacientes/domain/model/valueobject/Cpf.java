package br.dev.paulowolfgang.pacientes.domain.model.valueobject;

import br.dev.paulowolfgang.pacientes.domain.exception.DomainException;

import java.util.Objects;

public record Cpf(String value)
{
    public static final int QUANTIDADE_DIGITOS_CPF = 11;

    public Cpf
    {
        Objects.requireNonNull(value, "cpf");
        String digits = value.replaceAll("\\D", "");

        if(digits.length() != QUANTIDADE_DIGITOS_CPF) throw new DomainException("O CPF informado deve ter 11 dígitos.");

        value = digits;
    }

    @Override
    public String value()
    {
        return value;
    }
}
