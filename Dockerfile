FROM maven:3.9.1 AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:17 as image-jira

LABEL authors="sergey"
RUN mkdir /app
COPY --from=build app/target/jira-1.0.jar /app/jira-1.0.jar
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} jira-1.0.jar
WORKDIR /app
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/jira-1.0.jar"]
