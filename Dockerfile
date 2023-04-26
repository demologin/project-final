FROM maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
COPY resources ./resources
CMD  export $(cat .env | xargs)
RUN mvn clean package -DskipTests
RUN mv ./target/*.jar ./jira.jar
ENTRYPOINT ["java","-jar","/app/jira.jar", "--spring.profiles.active=prod"]