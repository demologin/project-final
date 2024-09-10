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

Список выполненных задач:
- Удалить социальные сети
- Вынести чувствительную информацию в отдельный проперти файл
- Добавить testcontainers
- Написать тесты для всех публичных методов контроллера ProfileRestController
- Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload
- Добавить новый функционал: добавления тегов к задаче(без фронта)
- Добавить подсчет времени сколько задача находилась в работе и тестировании.
- Написать Dockerfile для основного сервера
- Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx
- Добавить локализацию минимум на двух языках(en,es,ru) для шаблонов писем (mails) и стартовой страницы index.html