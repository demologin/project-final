FROM openjdk:17

WORKDIR /project-final

COPY target/jira-1.0.jar /project-final/jira-1.0.jar

EXPOSE 8080

CMD ["java", "-jar", "jira-1.0.jar"]