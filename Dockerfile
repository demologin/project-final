FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY pom.xml ./pom.xml
RUN apk add --no-cache maven
RUN mvn dependency:go-offline -B
COPY src ./src
COPY resources ./resources

RUN mvn clean package -Pprod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "--spring.profiles.active=prod", "app/target/jira.jar"]

