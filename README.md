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
1.Разобрался со структурой проекта.
2.Удалил все упоминания vk и yandex, включая БД.
3.Вынес чувствительную информацию (логин, пароль БД, идентификаторы для OAuth регистрации/авторизации, настройки почты) в отдельный проперти файл.
4.Переписал тесты на тестконтеинеры.
5.Написал тесты для класса ProfileRestController.
6.Добавил dockerfile.
7.Добавил docker-compose.
8.Добавлен пейджинг для страницы админа при просмотре существующих пользователей.
9.Реализовано добавление тегов на беке,создал метод в DashboardUIController под localhost:8080/{id} URL, создал метод в TaskService для добавления тега и записи в бд.
Запускается по вставленному URL в браузере с параметром tag.

