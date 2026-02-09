package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.CpfAlreadyExistsException;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.CreatePacienteCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.EnderecoCommand;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePacienteServiceTest
{
    private PacienteRepositoryPort repository;
    private CreatePacienteService service;

    @BeforeEach
    void setup()
    {
        repository = mock(PacienteRepositoryPort.class);
        service = new CreatePacienteService(repository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existir.")
    void shouldThrowWhenCpfAlreadyExists()
    {
        // arrange
        var command = new CreatePacienteCommand(
                "Maria Silva",
                "123.456.789-10",
                LocalDate.of(1995, 7, 10),
                "maria@teste.com",
                "91999999999",
                null
        );

        when(repository.existsByCpf(any(Cpf.class))).thenReturn(true);

        // act + assert
        assertThrows(CpfAlreadyExistsException.class, () -> service.execute(command));

        verify(repository).existsByCpf(any(Cpf.class));
        verify(repository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve criar paciente quando CPF não existir e retornar result mapeado.")
    void shouldCreatePacienteWhenCpfDoesNotExist()
    {
        // arrange
        var command = new CreatePacienteCommand(
                "Maria Silva",
                "12345678910",
                LocalDate.of(1995, 7, 10),
                "maria@teste.com",
                "91999999999",
                new EnderecoCommand(
                        "Av. Nazaré",
                        "1000",
                        null,
                        "Nazaré",
                        "Belém",
                        "PA",
                        "66035-170"
                )
        );

        when(repository.existsByCpf(any(Cpf.class))).thenReturn(false);

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        PacienteResult result = service.execute(command);

        // assert
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Maria Silva", result.nome());
        assertEquals("12345678910", result.cpf());
        assertEquals(LocalDate.of(1995, 7, 10), result.dataNascimento());
        assertEquals("maria@teste.com", result.email());
        assertEquals("91999999999", result.telefone());
        assertTrue(result.ativo());

        assertNotNull(result.endereco());
        assertEquals("Av. Nazaré", result.endereco().logradouro());
        assertEquals("1000", result.endereco().numero());
        assertNull(result.endereco().complemento());
        assertEquals("Nazaré", result.endereco().bairro());
        assertEquals("Belém", result.endereco().cidade());
        assertEquals("PA", result.endereco().uf());
        assertEquals("66035170", result.endereco().cep());

        verify(repository).existsByCpf(any(Cpf.class));
        verify(repository).save(captor.capture());

        Paciente savedPaciente = captor.getValue();
        assertNotNull(savedPaciente.getId());
        assertEquals("Maria Silva", savedPaciente.getNome());
        assertEquals("12345678910", savedPaciente.getCpf().value());
        assertEquals(LocalDate.of(1995, 7, 10), savedPaciente.getDataNascimento());
        assertNotNull(savedPaciente.getEndereco());
        assertEquals("66035170", savedPaciente.getEndereco().cep());
    }

    @Test
    @DisplayName("Deve permitir criar paciente sem email/telefone/endereco.")
    void shouldCreatePacienteWithNullOptionalFields()
    {
        // arrange
        var command = new CreatePacienteCommand(
                "João Souza",
                "98765432100",
                LocalDate.of(2000, 1, 1),
                null,
                null,
                null
        );

        when(repository.existsByCpf(any(Cpf.class))).thenReturn(false);
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        PacienteResult result = service.execute(command);

        // assert
        assertNotNull(result.id());
        assertEquals("João Souza", result.nome());
        assertEquals("98765432100", result.cpf());
        assertEquals(LocalDate.of(2000, 1, 1), result.dataNascimento());
        assertNull(result.email());
        assertNull(result.telefone());
        assertNull(result.endereco());

        verify(repository).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Deve normalizar CEP removendo caracteres especiais antes de salvar.")
    void shouldNormalizeCepBeforeSaving()
    {
        // arrange
        var command = new CreatePacienteCommand(
                "Ana Lima",
                "11122233344",
                LocalDate.of(1999, 12, 31),
                null,
                null,
                new EnderecoCommand(
                        "Rua X",
                        "10",
                        null,
                        "Centro",
                        "Belém",
                        "PA",
                        "66.035-170"
                )
        );

        when(repository.existsByCpf(any(Cpf.class))).thenReturn(false);

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        when(repository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        service.execute(command);

        // assert
        verify(repository).save(captor.capture());
        Endereco endereco = captor.getValue().getEndereco();
        assertNotNull(endereco);
        assertEquals("66035170", endereco.cep());
    }
}
