package br.dev.paulowolfgang.pacientes.domain.model.valueobject;

import br.dev.paulowolfgang.pacientes.domain.exception.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value)
{
    private static final Pattern SIMPLE = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    public static final int TAMANHO_MAXIMO_EMAIL = 150;

    public Email
    {
        Objects.requireNonNull(value, "email");
        String v = value.trim().toLowerCase();

        if (v.isBlank()) throw new DomainException("O campo de E-MAIL não pode estar em branco.");
        if (v.length() > TAMANHO_MAXIMO_EMAIL) throw new DomainException("O campo de E-MAIL deve ter no máximo 150 caracteres.");
        if (!SIMPLE.matcher(v).matches()) throw new DomainException("O formato de E-MAIL é inválido.");

        value = v;
    }
}
