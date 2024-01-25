# Stage 1: Build with Maven
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean install

# Stage 2: Create final image
FROM openjdk:11-jdk-slim

WORKDIR /app

COPY --from=build /app/target/AppRH-0.0.1-SNAPSHOT.jar appSemear.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "appSemear.jar"]
