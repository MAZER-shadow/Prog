# Этап сборки с Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Этап запуска (только JAR)
FROM openjdk:17-jdk-slim
WORKDIR /app2
COPY --from=build /app/server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 12345
CMD ["java", "-jar", "app.jar"]