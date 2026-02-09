package br.dev.paulowolfgang.pacientes.app.exception;

public class CpfAlreadyExistsException extends RuntimeException
{
    public CpfAlreadyExistsException(String cpf)
    {
        super("CPF já cadastrado: " + cpf);
    }
}
