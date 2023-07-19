# Используем официальный образ с OpenJDK 19
FROM openjdk:19

# Определяем рабочую директорию в контейнере
WORKDIR /project-final

# Копируем файл .jar в рабочую директорию контейнера
COPY target/jira-1.0.jar /project-final/jira-1.0.jar

# Объявляем, что контейнер будет слушать на указанном порту во время выполнения
EXPOSE 8080

# Задаем команду для запуска приложения при старте контейнера
CMD ["java", "-jar", "jira-1.0.jar"]