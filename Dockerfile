FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-11-jdk -v
COPY . .

RUN apt-get install maven -v
RUN mvn clean install

FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY --from=build /target/AppRH-0.0.1-SNAPSHOT.jar appSemear.jar

ENTRYPOINT [ "java", "-jar", "appSemear.jar" ]