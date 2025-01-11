## BUILD Stage ##
FROM gradle:jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Environment Variables (falls benötigt)
ARG DB_PASSWORD
ARG DB_URL
ARG DB_USER

# Gradle-Build ausführen
RUN ./gradlew build --no-daemon

## PACKAGE Stage ##
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Kopiere das gebaute JAR-File aus der Build-Stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Port öffnen (falls erforderlich)
EXPOSE 8080

# Anwendung starten
ENTRYPOINT ["java", "-jar", "app.jar"]
