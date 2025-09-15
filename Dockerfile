FROM maven:3.9.9-eclipse-temurin-17 AS build
LABEL authors="rolli"
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:17-slim
LABEL authors="rolli"
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]