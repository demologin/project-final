FROM openjdk:17-jdk-alpine
MAINTAINER d.shubchynskyi@gmail.com
WORKDIR /app
COPY resources ./resources
COPY target/jira-1.0.jar jira-1.0.jar
CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-default} /app/jira-1.0.jar

# TODO task 9 - Dockerfile
