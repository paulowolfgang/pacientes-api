package br.dev.paulowolfgang.pacientes.infra.web.controller;

import br.dev.paulowolfgang.pacientes.app.port.in.*;
import br.dev.paulowolfgang.pacientes.app.port.in.dto.PacienteResult;
import br.dev.paulowolfgang.pacientes.infra.web.dto.PacienteRequest;
import br.dev.paulowolfgang.pacientes.infra.web.dto.PacienteUpdateRequest;
import br.dev.paulowolfgang.pacientes.infra.web.mapper.PacienteWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController
{
    private final CreatePacienteUseCase createUseCase;
    private final GetPacienteUseCase getUseCase;
    private final ListPacientesUseCase listUseCase;
    private final UpdatePacienteUseCase updateUseCase;
    private final DeletePacienteUseCase deleteUseCase;

    public PacienteController(CreatePacienteUseCase createUseCase,
                              GetPacienteUseCase getUseCase,
                              ListPacientesUseCase listUseCase,
                              UpdatePacienteUseCase updateUseCase,
                              DeletePacienteUseCase deleteUseCase)
    {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<PacienteResult> create(@Valid @RequestBody PacienteRequest request)
    {
        var result = createUseCase.execute(PacienteWebMapper.toCreateCommand(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public PacienteResult getById(@PathVariable UUID id)
    {
        return getUseCase.execute(id);
    }

    @GetMapping
    public List<PacienteResult> list()
    {
        return listUseCase.execute();
    }

    @PutMapping("/{id}")
    public PacienteResult update(@PathVariable UUID id, @Valid @RequestBody PacienteUpdateRequest request)
    {
        return updateUseCase.execute(id, PacienteWebMapper.toUpdateCommand(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id)
    {
        deleteUseCase.execute(id);
    }
}
