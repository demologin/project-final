FROM openjdk:17-jdk

COPY target/jira-1.0.jar /app/jira-1.0.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "jira-1.0.jar"]