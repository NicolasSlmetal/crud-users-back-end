FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .
RUN ["mvn", "clean", "install"]

FROM openjdk:17 as run

WORKDIR /app
ARG JAR_HOME=users-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/$JAR_HOME app.jar
ENTRYPOINT  [ "java", "-jar", "app.jar" ]


