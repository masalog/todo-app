# ===== Build Stage =====
FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

# テストを含めて検証
RUN mvn verify

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/target/quarkus-app/lib/ ./lib/
COPY --from=builder /build/target/quarkus-app/*.jar ./
COPY --from=builder /build/target/quarkus-app/app/ ./app/
COPY --from=builder /build/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]