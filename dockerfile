# ===== Build Stage =====
FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre

RUN useradd -r -u 1001 appuser

WORKDIR /app

COPY --from=builder /build/target/*-runner.jar app.jar

RUN chown -R appuser:appuser /app

USER appuser

EXPOSE 8081

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75","-XX:+ExitOnOutOfMemoryError","-jar","app.jar"]