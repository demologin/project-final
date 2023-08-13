FROM eclipse-temurin:17-jdk-alpine
COPY resources ./resources
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]