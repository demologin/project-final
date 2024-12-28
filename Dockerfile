FROM openjdk:17.0.2-jdk-slim-buster

ARG JAR_FILE=target/*.jar
ARG RESOURCES=resources
COPY ${JAR_FILE} app.jar
COPY ${RESOURCES} /resources

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","app.jar"]