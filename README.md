## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Напоминание для себя: разобраться детально с Docker и все что с ним связано.

Список выполненных задач:
1. Разобрался со структурой проекта (onboarding).
2. Удалил социальные сети. Удалил кнопки на фронте. Удалил часть конфигурационных данных в yaml файле.
Удалил классы, отвечающие за логику.
3. Вынес чувствительную информацию в отдельный файл и прописал переменные окружения.
4. Написал тесты для публичных методов ProfileRestController.
5. Выполнил рефакторинг com.javarush.jira.bugtracking.attachment.FileUtil#upload.
6. Добавил новый функционал к (REST API) и добавил методы в TaskService.
7. Добавлена локализация(EN, RU, DE)
8. Добавлен файл Dockerfile.
9. Добавил файл docker-compose.