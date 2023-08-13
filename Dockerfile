# todo 9 Написать Dockerfile для основного сервера

FROM maven
WORKDIR /
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
COPY resources ./resources
RUN mvn clean package -DskipTests
RUN mv ./target/*.jar ./jira.jar
ENTRYPOINT ["java", "-jar", "/jira.jar", "--spring.profiles.active=prod"]