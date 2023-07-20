#TODO task 9 add Dockerfile
FROM maven:3.9-eclipse-temurin-17
WORKDIR /app
COPY resources ./resources
COPY src ./src
COPY target/*.jar jira-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/jira-1.0.jar"]