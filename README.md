# API de Pacientes

API de pacientes desenvolvida como desafio técnico.

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento, teste ou execução.

### 📋 Pré-requisitos

Você precisará de:

- Java 21 LTS ou superior
- Maven 3.8+ (opcional, se for rodar localmente)
- Docker (opcional, se for rodar em container)

## ▶️ Como executar a aplicação

### ✅ Opção 1: Rodar localmente com Maven

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio

2. Compile o projeto
    
    ```bash
   ./mvnw clean install

3. Execute a aplicação
    
    ```bash
   ./mvnw spring-boot:run

OBS: No Windows, use mvnw.cmd no lugar de ./mvnw.

### 🐳 Opção 2: Rodar com Docker

1. Construa a imagem e execute o container a partir do Docker Compose:

    ```bash
   docker compose up --build

OBS: A aplicação estará acessível em http://localhost:8080

### 📚 Observações
Swagger – Documentação da API

Interface Swagger UI: http://localhost:8080/swagger-ui/index.html

Documento OpenAPI (JSON): http://localhost:8080/v3/api-docs

### H2 Console – Banco de Dados em Memória
URL de acesso: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:pacientesdb

Usuário: sa

Senha: (deixe em branco)

### 🧪 Executando os testes

1. Para rodar os testes da aplicação:

   ```bash
   ./mvnw test 

### 🛠️ Construído com

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Swagger - springdoc-openapi](https://springdoc.org/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

## Respostas do Desafio

### 1) Como você escolheria a stack tecnológica para esse projeto?

Eu escolheria a stack com base em objetivo do produto, contexto do time, requisitos não funcionais (segurança, escalabilidade, observabilidade) e custo de operação.

* **Java 21 + Spring Boot 4**: produtividade, ecossistema maduro, integração fácil com Web/JPA/Validation e ótima base para evoluir (segurança, observabilidade, etc.).
* **H2 (in-memory)**: simplifica execução local e testes, sem dependência externa.
* **Flyway**: versionamento de schema e repetibilidade do ambiente.
* **JPA/Hibernate**: praticidade para CRUD; possibilidade de evoluir com queries mais complexas.
* **Bean Validation**: validação consistente na borda da API.
* **OpenAPI/Swagger (springdoc)**: documentação automática e testável via UI.
* **Arquitetura Hexagonal + DDD**: domínio isolado, fácil de testar e adaptar a persistência.

---

### 2) Quais critérios usa para definir arquitetura de backend, frontend e mobile?

Eu costumo definir arquitetura e design das aplicações com base nos seguintes parâmetros:

* **Complexidade do domínio e regras de negócio**
* **Escalabilidade e evolução** (novos módulos, integrações, times paralelos)
* **Manutenibilidade** (testabilidade, separação de responsabilidades)
* **Integrações e dependências externas**
* **Time e maturidade técnica**

---

### 3) Como garantir qualidade de código na equipe?

Combinando boas práticas de desenvolvimento, processos e automação:

* **Code Review com checklist**.
* **Testes automatizados** (unitários no domínio/usecases; integração e contrato quando necessário).
* **CI com qualidade mínima**: build, testes e cobertura.
* **SonarQube/SonarCloud** ou similar: code smells, duplicidade, complexidade, vulnerabilidades.
* **Observabilidade e logs estruturados** para facilitar debugging.

---

### 4) Como você define priorização de tarefas em um sprint?

Eu costumo priorizar pelo valor do negócio após alinhar com o analista de requisitos ou PO:

* O que entrega mais valor ao usuário/cliente primeiro.
* Dependências: liberar APIs e contratos cedo para destravar outros times.

---

### 5) Qual sua estratégia para gerenciar integrações com serviços externos?

Para o caso de uma aplicação desenvolvida com uma arquitetura hexagonal ou limpa, eu trato integração como fronteira do sistema:

* **Ports/Adapters (Hexagonal)**: o core não depende do fornecedor.
* **Contratos e mocks**: WireMock/contract tests para não depender do serviço real em testes.
* **Versionamento de APIs externas** e wrapper interno para reduzir impacto de mudanças.
* **Observabilidade**: métricas e logs com correlation-id para rastrear chamadas.

---

### 6) Como você lidaria com falhas em produção?

Geralmente os times costumam ter um plano prático para incidentes:

* **Detecção**: logs estruturados, métricas (latência, erro, throughput), alertas e tracing.
* **Diagnóstico**: análise de logs + tracing + dashboards, reproduzir em ambiente de staging.
* **Correção**: hotfix com testes, validação e deploy controlado.

---

### 7) Qual abordagem adotaria para CI/CD nessa API?

* **CI (a cada PR)**:

   * `mvn clean test`
   * análise estática
   * validação de cobertura mínima (quando aplicável)
   * build do artefato e (opcional) imagem Docker
* **CD**:

   * deploy automatizado em **staging**
   * deploy em produção
   * rollback automático em falha

GitHub Actions, Gitlab CI/CD ou Azure Devops.

---

### 8) Como você decide entre REST, GraphQL ou outra forma de API?

Eu decido baseado no tipo de consumo e padrões do domínio:

* **REST**: excelente para CRUD e recursos bem definidos. Simples, cacheável, fácil de observar e governar.
* **GraphQL**: útil quando há múltiplos clientes com necessidades diferentes de payload, especialmente em produtos com várias telas/consultas combinadas.

---

### 9) Como avalia desempenho e otimização de APIs?

* **Profiling**: identificar gargalos reais (queries, serialização, GC).
* **Banco**: índices, evitar N+1, paginação, DTOs.
* **Cache** quando fizer sentido.
* **Boas práticas**: timeouts, limites de payload, paginação, pool de conexões.

---

### 10) Como você documenta decisões técnicas e garante o conhecimento compartilhado na equipe?

* **README**: como rodar, endpoints, arquitetura, decisões principais.
* **Diagramas simples** (casos de uso, classes) quando o projeto cresce.
* **Conventions**: padrões de branch, commits, PR templates.
* **Rotina de compartilhamento**: reuniões curtas para alinhamentos, revisões de arquitetura.
* **Documentos técnicos**: Postman doc, swagger, scripts de sql).
