# --------- BUILD ---------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o necessário
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copia o código
COPY src ./src
RUN mvn -q -DskipTests clean package

# --------- RUNTIME ---------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Segurança básica
RUN addgroup --system app && adduser --system --ingroup app app
USER app

# Copia o jar gerado
COPY --from=build /app/target/*.jar app.jar

# Porta da API
EXPOSE 8080

# Start
ENTRYPOINT ["java","-jar","/app/app.jar"]
