## BUILD Stage ##
FROM gradle:jdk17 as build
WORKDIR /app
COPY . .

# Gradle-Build ausführen
RUN gradle build --no-daemon

## PACKAGE Stage ##
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Port konfigurieren
EXPOSE 8080

# Startbefehl
ENTRYPOINT ["java", "-jar", "app.jar"]
