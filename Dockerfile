FROM openjdk:17-jdk-alpine
MAINTAINER d.shubchynskyi@gmail.com

WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
#RUN mvn dependency:go-offline -B

# Копируем исходный код и ресурсы приложения в образ
COPY src ./src
COPY resources ./resources
COPY target/jira-1.0.jar jira-1.0.jar

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

ENTRYPOINT ["dockerize", "-wait", "tcp://jira-postgres-db:5432", "-timeout", "60s", "java", "-jar", "/app/jira-1.0.jar"]


# TODO Dockerfile and nginx settings

## Собираем проект
#RUN mvn package
#
## Создаем образ с Java 11
#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
## Копируем jar-файл из первого этапа
#COPY --from=build /app/target/*.jar /app/jira-1.0.jar
#
#EXPOSE 8080
#
#CMD ["java", "-jar", "/app/jira-1.0.jar"]
