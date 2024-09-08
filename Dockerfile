FROM maven:3.9.6-amazoncorretto-17-al2023
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY resources ./resources
RUN mvn clean package -Pprod
RUN mv ./target/*.jar ./jira.jar
RUN rm -rf ./tagret
ENTRYPOINT ["java", "-jar","/app/jira.jar", "--spring.profiles.active=prod"]