# Stage 1: Build
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copia i file di configurazione di Gradle
COPY build.gradle.kts settings.gradle.kts gradlew* ./
COPY gradle gradle

# Dai i permessi di esecuzione allo script gradlew
RUN chmod +x gradlew

# Scarica le dipendenze (cache)
RUN ./gradlew dependencies || return 0

# Copia tutto il resto e builda
COPY . .
RUN ./gradlew clean build -x test

# Stage 2: Run
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
