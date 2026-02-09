package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletePacienteServiceTest
{
    private PacienteRepositoryPort repository;
    private DeletePacienteService service;

    @BeforeEach
    void setup()
    {
        repository = mock(PacienteRepositoryPort.class);
        service = new DeletePacienteService(repository);
    }

    @Test
    @DisplayName("Deve deletar paciente quando ele existir.")
    void shouldDeleteWhenPacienteExists()
    {
        // arrange
        UUID id = UUID.randomUUID();

        Paciente paciente = Paciente.reconstituir(
                id,
                "Maria",
                new Cpf("12345678910"),
                LocalDate.of(1995, 7, 10),
                null,
                null,
                null,
                true
        );

        when(repository.findById(id)).thenReturn(Optional.of(paciente));

        // act
        service.execute(id);

        // assert
        verify(repository).findById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando paciente não existir e não chamar delete.")
    void shouldThrowWhenPacienteNotFound()
    {
        // arrange
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(PacienteNotFoundException.class, () -> service.execute(id));

        verify(repository).findById(id);
        verify(repository, never()).deleteById(any(UUID.class));
        verifyNoMoreInteractions(repository);
    }
}
