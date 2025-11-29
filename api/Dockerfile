# --- ESTÁGIO 1: BUILD (Compilar o projeto com Java 21) ---
# Usa imagem Maven com Java 21 oficial
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências (Cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila o projeto
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: RUN (Rodar com Java 21 leve) ---
# Usa imagem Alpine com Java 21 (Muito mais leve e rápida)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Pega o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Inicia
ENTRYPOINT ["java", "-jar", "app.jar"]