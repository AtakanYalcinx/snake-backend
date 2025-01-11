## BUILD Stage ##
FROM gradle:jdk21-jammy AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .

# Gradle-Build ausführen
RUN ./gradlew build --no-daemon

## PACKAGE Stage ##
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Kopiere das gebaute JAR-File
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Port öffnen (Spring Boot nutzt standardmäßig 8080)
EXPOSE 8080

# Anwendung starten
ENTRYPOINT ["java", "-jar", "app.jar"]
