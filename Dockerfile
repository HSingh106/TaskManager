FROM openjdk:22-jdk-alpine
MAINTAINER Harman
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]