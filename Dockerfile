# Build step
FROM maven:3.8.4 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package

# Ejecution step
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
RUN mkdir /app/logs
RUN chmod -R 777 /app/logs
VOLUME /app/logs

COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8761
CMD ["java", "-jar", "/app/app.jar"]
