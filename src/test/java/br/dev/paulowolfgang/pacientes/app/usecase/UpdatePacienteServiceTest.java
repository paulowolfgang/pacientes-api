package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.UpdatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Email;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePacienteServiceTest
{
    private PacienteRepositoryPort repository;
    private UpdatePacienteService service;

    @BeforeEach
    void setup()
    {
        repository = mock(PacienteRepositoryPort.class);
        service = new UpdatePacienteService(repository);
    }

    @Test
    @DisplayName("Deve lançar PacienteNotFoundException quando não encontrar o paciente.")
    void shouldThrowWhenNotFound()
    {
        // arrange
        UUID id = UUID.randomUUID();
        var command = new UpdatePacienteCommand(
                "Nome",
                LocalDate.of(2000, 1, 1),
                null,
                null,
                null,
                null
        );

        when(repository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(PacienteNotFoundException.class, () -> service.execute(id, command));

        verify(repository).findById(id);
        verify(repository, never()).save(any(Paciente.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve atualizar dados do paciente e salvar (incluindo normalização do CEP).")
    void shouldUpdateFieldsAndSave()
    {
        // arrange
        UUID id = UUID.randomUUID();

        Paciente existente = Paciente.reconstituir(
                id,
                "Maria Silva",
                new Cpf("12345678910"),
                LocalDate.of(1995, 7, 10),
                new Email("maria@teste.com"),
                new Telefone("91999999999"),
                new Endereco("Av. Nazaré", "1000", null, "Nazaré", "Belém", "PA", "66035170"),
                true
        );

        var command = new UpdatePacienteCommand(
                "Maria Silva Atualizada",
                LocalDate.of(1996, 8, 11),
                "maria.nova@teste.com",
                "(91) 98888-7777",
                new EnderecoCommand(
                        "Rua dos Mundurucus",
                        "200",
                        "Apto 101",
                        "Batista Campos",
                        "Belém",
                        "PA",
                        "66055-000"
                ),
                null // não mexe no ativo
        );

        when(repository.findById(id)).thenReturn(Optional.of(existente));

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        PacienteResult result = service.execute(id, command);

        // assert
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Maria Silva Atualizada", result.nome());
        assertEquals("12345678910", result.cpf()); // CPF não muda no update
        assertEquals(LocalDate.of(1996, 8, 11), result.dataNascimento());
        assertEquals("maria.nova@teste.com", result.email());
        assertEquals("91988887777", result.telefone()); // domínio normaliza para dígitos
        assertTrue(result.ativo()); // manteve

        assertNotNull(result.endereco());
        assertEquals("Rua dos Mundurucus", result.endereco().logradouro());
        assertEquals("200", result.endereco().numero());
        assertEquals("Apto 101", result.endereco().complemento());
        assertEquals("Batista Campos", result.endereco().bairro());
        assertEquals("Belém", result.endereco().cidade());
        assertEquals("PA", result.endereco().uf());
        assertEquals("66055000", result.endereco().cep()); // normalizado

        verify(repository).findById(id);
        verify(repository).save(captor.capture());
        verifyNoMoreInteractions(repository);

        Paciente salvo = captor.getValue();
        assertEquals("Maria Silva Atualizada", salvo.getNome());
        assertEquals(LocalDate.of(1996, 8, 11), salvo.getDataNascimento());
        assertNotNull(salvo.getEmail());
        assertEquals("maria.nova@teste.com", salvo.getEmail().value());
        assertNotNull(salvo.getTelefone());
        assertEquals("91988887777", salvo.getTelefone().value());

        assertNotNull(salvo.getEndereco());
        assertEquals("66055000", salvo.getEndereco().cep());
        assertTrue(salvo.isAtivo());
    }

    @Test
    @DisplayName("Deve ativar o paciente quando o 'command.ativo' = true")
    void shouldActivateWhenAtivoTrue()
    {
        // arrange
        UUID id = UUID.randomUUID();

        Paciente existente = Paciente.reconstituir(
                id,
                "João",
                new Cpf("98765432100"),
                LocalDate.of(2000, 1, 1),
                null,
                null,
                null,
                false // começa desativado
        );

        var command = new UpdatePacienteCommand(
                "João",
                LocalDate.of(2000, 1, 1),
                null,
                null,
                null,
                true
        );

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        PacienteResult result = service.execute(id, command);

        // assert
        assertTrue(result.ativo());
        verify(repository).findById(id);
        verify(repository).save(any(Paciente.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve desativar paciente quando o 'command.ativo' = false")
    void shouldDeactivateWhenAtivoFalse()
    {
        // arrange
        UUID id = UUID.randomUUID();

        Paciente existente = Paciente.reconstituir(
                id,
                "Maria",
                new Cpf("12345678910"),
                LocalDate.of(1995, 7, 10),
                null,
                null,
                null,
                true // começa ativo
        );

        var command = new UpdatePacienteCommand(
                "Maria",
                LocalDate.of(1995, 7, 10),
                null,
                null,
                null,
                false
        );

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        PacienteResult result = service.execute(id, command);

        // assert
        assertFalse(result.ativo());
        verify(repository).findById(id);
        verify(repository).save(any(Paciente.class));
        verifyNoMoreInteractions(repository);
    }
}
