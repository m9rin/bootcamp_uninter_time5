# =========================
# Stage: dev
# Ambiente de desenvolvimento
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS dev
WORKDIR /app

RUN apt-get update && apt-get install -y \
    git \
    curl \
    unzip \
    procps \
    iputils-ping \
    netcat-openbsd \
    default-mysql-client \
    && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

CMD ["sleep", "infinity"]


# =========================
# Stage: build
# Compila a aplicação
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -q -DskipTests package


# =========================
# Stage: prod
# Imagem final de execução
# =========================
FROM eclipse-temurin:21-jre AS prod
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]