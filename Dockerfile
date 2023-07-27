#TODO Task 9: Dockerfile for main server.
FROM openjdk:17.0.2
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY resources ./resources
COPY ${JAR_FILE} jira-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/jira-1.0.jar", "--spring.profiles.active=prod"]
