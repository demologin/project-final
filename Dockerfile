#TODO task 9 add Dockerfile
FROM openjdk:17
WORKDIR /app
COPY resources ./resources
COPY target/*.jar jira-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/jira-1.0.jar"]