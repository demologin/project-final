
FROM openjdk:17-jdk
COPY target/*.jar app.jar
COPY resources /resources
EXPOSE 8080
ENV DB_URL=jdbc:postgresql://localhost:5432/jira
ENV DB_USER=jira
ENV DB_PASSWORD=JiraRush
ENTRYPOINT ["java", "-jar","app.jar"]