# Этап сборки с Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./config ./config
COPY ./pom.xml ./pom.xml
COPY ./common/pom.xml ./common/pom.xml
COPY ./client/pom.xml ./client/pom.xml
COPY ./server/pom.xml ./server/pom.xml
COPY ./transaction-manager/pom.xml ./transaction-manager/pom.xml

RUN mvn dependency:go-offline -B

COPY ./client/src ./client/src
COPY ./common/src ./common/src
COPY ./transaction-manager/src ./transaction-manager/src
COPY ./server/src ./server/src

RUN mvn clean install -DskipTests
# Этап запуска (только JAR)
FROM openjdk:17-jdk-slim
WORKDIR /
COPY --from=build /app/server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 12345
COPY ./server/src/main/resources/configwithpostgres .
CMD ["java", "-jar", "-Ddb.config=./configwithpostgres", "app.jar"]
