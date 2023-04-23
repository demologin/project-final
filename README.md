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
  - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем проверять

## Аналоги
- https://java-source.net/open-source/issue-trackers

## Тестирование
- https://habr.com/ru/articles/259055/

Список выполненных задач:
1) Добавил docker-compose файл для развёртывания базы данных.
2) Добавил второй внешний файл конфигурации (secure.yaml).
3) Вынес во второй внешний файл конфигурации все чувствительные креды и заменил значения на переменные окружения.
4) Добавил в test.com.javarush.jira.JiraRushApplicationTests настройку отдельного контекста для тестов. Этот контекст смотрит на тест-контейнер с Postgres, создаваемый библиотекой testcontainers и запускаем все  тесты в нём.
5) 