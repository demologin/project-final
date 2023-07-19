FROM openjdk:17-jdk-alpine
MAINTAINER d.shubchynskyi@gmail.com
WORKDIR /app
COPY resources ./resources
COPY target/jira-1.0.jar jira-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/jira-1.0.jar"]

# TODO 9 - Dockerfile
