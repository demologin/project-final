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

- 1 Разобраться со структурой проекта.
- 2 Удалить социальные сети: vk, yandex.
    - deleted handlers and view parts
- 3 Вынести чувствительную информацию в отдельный проперти файл.
  Значения этих проперти должны считываться при старте сервера из переменных окружения машины
    - removed sensitive property to sensitive.yaml use env vars
    - run without env vars will be used default params
- 4 Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL. Для этого нужно
  определить 2 бина, и выборка какой из них использовать должно определяться активным профилем Spring. 
    - used testcontainers
- 5 Написать тесты для всех публичных методов контроллера ProfileRestController. Хоть методов только 2, но тестовых методов должно быть больше, т.к. нужно проверить success and unsuccess path.
    - add test in ProfileRestControllerTest (getUnauthorized, getUserProfile, getGuestProfile, createProfile, updateProfile, updateInvalidProfile)
- 6 Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный
  подход для работы с файловой системмой.
    - refactored upload method
- 7 Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе). Фронт делать необязательно.
  Таблица task_tag уже создана.
    - added Post method addTaskTag in TaskUIController
    - added Post method addTaskTag in TaskController, swagger-ui documentation
    - TaskService now add tags to task and update repository
    - add test in TaskControllerTest
- 9 Написать Dockerfile для основного сервера
    - add Dockerfile based on maven:3.9-eclipse-temurin-17
- 10 Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx. Для nginx используй конфиг-файл config/nginx.conf. При необходимости файл конфига можно редактировать.
    - add docker-compose.yaml file with nginx, postgresql, server app (to run application with containers "docker-compose up")
- 11 Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html.
    - added localization on EN, UA, RU languages (mail-confirmation.html, password-reset.html, footer.html, header.html, register.html index.html, login.html)
    - added buttons to change localization

