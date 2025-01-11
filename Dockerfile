## BUILD Stage ##
FROM gradle:jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Environment Variables (können später genutzt werden)
ARG DB_PASSWORD
ARG DB_URL
ARG DB_USER

# Gradle-Build ausführen
RUN gradle build --no-daemon

## PACKAGE Stage ##
FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /home/gradle/src/build/libs/webtech-ws2425-backend-0.0.1-SNAPSHOT.jar app.jar

# Anwendung starten
ENTRYPOINT ["java", "-jar", "app.jar"]
