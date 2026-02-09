package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Email;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Endereco;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPacienteServiceTest
{
    private PacienteRepositoryPort repository;
    private GetPacienteService service;

    @BeforeEach
    void setup()
    {
        repository = mock(PacienteRepositoryPort.class);
        service = new GetPacienteService(repository);
    }

    @Test
    @DisplayName("Deve retornar paciente quando ele existir.")
    void shouldReturnPacienteWhenExists()
    {
        // arrange
        UUID id = UUID.randomUUID();

        Paciente paciente = Paciente.reconstituir(
                id,
                "Maria Silva",
                new Cpf("12345678910"),
                LocalDate.of(1995, 7, 10),
                new Email("maria@teste.com"),
                new Telefone("91999999999"),
                new Endereco(
                        "Av. Nazaré",
                        "1000",
                        null,
                        "Nazaré",
                        "Belém",
                        "PA",
                        "66035170"
                ),
                true
        );

        when(repository.findById(id)).thenReturn(Optional.of(paciente));

        // act
        PacienteResult result = service.execute(id);

        // assert
        assertNotNull(result);
        assertEquals(id, result.id());
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

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve lançar PacienteNotFoundException quando ele não existir.")
    void shouldThrowWhenNotFound()
    {
        // arrange
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(PacienteNotFoundException.class, () -> service.execute(id));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}
