FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app


COPY pom.xml ./pom.xml
COPY src ./src
COPY resources ./resources

RUN mvn clean package -Pprod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "--spring.profiles.active=prod", "app/target/jira.jar"]

