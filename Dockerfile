FROM maven:3.9-amazoncorretto-17-al2023
COPY jira.jar app/jira.jar
COPY ./resources /app/resources
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "/app/jira.jar"]