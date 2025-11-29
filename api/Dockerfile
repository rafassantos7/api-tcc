# --- ESTÁGIO 1: BUILD (Compilar o projeto) ---
# Usa uma imagem com Maven e Java 17 para gerar o .jar
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para cachear as dependências (Otimização)
COPY pom.xml .
# Baixa as dependências (sem copiar o código fonte ainda)
RUN mvn dependency:go-offline

# Copia o código fonte do projeto
COPY src ./src

# Compila o projeto e gera o arquivo .jar (pulando testes para ser mais rápido no deploy)
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: RUN (Rodar a aplicação) ---
# Usa uma imagem leve (Alpine) apenas com o JRE para rodar o app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (Padrão do Spring Boot)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]