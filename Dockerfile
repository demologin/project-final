FROM maven:3.8.5-openjdk-17
COPY src /app/src
COPY pom.xml /app
COPY resources /resources
RUN mvn -f /app/pom.xml clean package -Pprod
RUN rm -rf /app/src
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/target/jira-1.0.jar"]