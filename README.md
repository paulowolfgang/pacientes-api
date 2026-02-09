# API de Pacientes

API de pacientes desenvolvida como desafio técnico.

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento, teste ou execução.

### 📋 Pré-requisitos

Você precisará de:

- Java 21 LTS ou superior
- Maven 3.8+ (opcional, se for rodar localmente)

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
