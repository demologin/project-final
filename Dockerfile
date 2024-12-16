FROM openjdk:17.0.2-jdk-slim-buster

ARG JAR_FILE=target/*.jar
ARG RESOURCES=resources
ARG ATTACHMENTS=attachments
COPY ${JAR_FILE} app.jar
COPY ${RESOURCES} /resources
COPY ${ATTACHMENTS} /attachments

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","app.jar"]