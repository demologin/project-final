FROM openjdk:21-oracle

ARG JAR_FILE=target/jira-1.0.jar
ARG ENV_FILE=.env
ARG RESOURCES=resources
ARG ATTACHMENTS=attachments
WORKDIR /app
COPY ${JAR_FILE} app.jar
COPY ${ENV_FILE} .
COPY ${RESOURCES} /resources
COPY ${ATTACHMENTS} /attachments

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar","app.jar"]