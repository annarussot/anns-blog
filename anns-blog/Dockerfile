FROM gradle:8.7-jdk21 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew* ./
COPY gradle gradle

RUN ./gradlew dependencies || return 0

COPY . .
RUN ./gradlew clean build -x test


FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
