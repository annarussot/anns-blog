# Stage 1: Build
FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /app

# Copia solo i file di configurazione per cache delle dipendenze
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle

# Download delle dipendenze (layer cacheable)
RUN gradle dependencies --no-daemon

# Copia il codice sorgente
COPY src src

# Build dell'applicazione
RUN gradle clean bootJar -x test --no-daemon --parallel

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine AS runtime

# Crea un utente non-root per sicurezza
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001 -G spring

# Installa curl per health check
RUN apk add --no-cache curl

WORKDIR /app

# Copia il jar con nome specifico
COPY --from=build --chown=spring:spring /app/build/libs/*.jar app.jar

# Cambia utente
USER spring:spring

# Espone la porta (Render usa PORT env var)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Configurazione JVM ottimizzata per container
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+UseG1GC", \
    "-XX:+UseStringDeduplication", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-Dserver.port=${PORT:-8080}", \
    "-jar", "app.jar"]