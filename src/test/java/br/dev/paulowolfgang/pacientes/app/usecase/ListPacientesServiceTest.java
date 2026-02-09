package br.dev.paulowolfgang.pacientes.app.usecase;

import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.domain.model.Paciente;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Cpf;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Email;
import br.dev.paulowolfgang.pacientes.domain.model.valueobject.Telefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListPacientesServiceTest
{
    private PacienteRepositoryPort repository;
    private ListPacientesService service;

    @BeforeEach
    void setup()
    {
        repository = mock(PacienteRepositoryPort.class);
        service = new ListPacientesService(repository);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houverem pacientes.")
    void shouldReturnEmptyListWhenNoPatients()
    {
        // arrange
        when(repository.findAll()).thenReturn(List.of());

        // act
        List<PacienteResult> result = service.execute();

        // assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Deve listar os pacientes (mapeados para PacienteResult).")
    void shouldListAndMapPatients()
    {
        // arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Paciente p1 = Paciente.reconstituir(
                id1,
                "Maria Silva",
                new Cpf("12345678910"),
                LocalDate.of(1995, 7, 10),
                new Email("maria@teste.com"),
                new Telefone("91999999999"),
                null,
                true
        );

        Paciente p2 = Paciente.reconstituir(
                id2,
                "João Souza",
                new Cpf("98765432100"),
                LocalDate.of(2000, 1, 1),
                null,
                null,
                null,
                false
        );

        when(repository.findAll()).thenReturn(List.of(p1, p2));

        // act
        List<PacienteResult> result = service.execute();

        // assert
        assertNotNull(result);
        assertEquals(2, result.size());

        PacienteResult r1 = result.get(0);
        assertEquals(id1, r1.id());
        assertEquals("Maria Silva", r1.nome());
        assertEquals("12345678910", r1.cpf());
        assertEquals(LocalDate.of(1995, 7, 10), r1.dataNascimento());
        assertEquals("maria@teste.com", r1.email());
        assertEquals("91999999999", r1.telefone());
        assertTrue(r1.ativo());

        PacienteResult r2 = result.get(1);
        assertEquals(id2, r2.id());
        assertEquals("João Souza", r2.nome());
        assertEquals("98765432100", r2.cpf());
        assertEquals(LocalDate.of(2000, 1, 1), r2.dataNascimento());
        assertNull(r2.email());
        assertNull(r2.telefone());
        assertFalse(r2.ativo());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }
}
