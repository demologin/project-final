FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY ./resources /resources
ENTRYPOINT ["java","-jar","/app.jar"]