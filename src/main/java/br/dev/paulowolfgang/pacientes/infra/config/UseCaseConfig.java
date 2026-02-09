package br.dev.paulowolfgang.pacientes.infra.config;

import br.dev.paulowolfgang.pacientes.app.port.in.*;
import br.dev.paulowolfgang.pacientes.app.port.out.PacienteRepositoryPort;
import br.dev.paulowolfgang.pacientes.app.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig
{
    @Bean
    public CreatePacienteUseCase createPacienteUseCase(PacienteRepositoryPort repo)
    {
        return new CreatePacienteService(repo);
    }

    @Bean
    public GetPacienteUseCase getPacienteUseCase(PacienteRepositoryPort repo)
    {
        return new GetPacienteService(repo);
    }

    @Bean
    public ListPacientesUseCase listPacientesUseCase(PacienteRepositoryPort repo)
    {
        return new ListPacientesService(repo);
    }

    @Bean
    public UpdatePacienteUseCase updatePacienteUseCase(PacienteRepositoryPort repo)
    {
        return new UpdatePacienteService(repo);
    }

    @Bean
    public DeletePacienteUseCase deletePacienteUseCase(PacienteRepositoryPort repo)
    {
        return new DeletePacienteService(repo);
    }
}
