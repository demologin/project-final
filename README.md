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
- Разобраться со структурой проекта (onboarding).
- Удалить социальные сети: vk, yandex.
  - удаление из файла application.yaml.
  - удаление из базы данных + добавление миграции.
  - удаление хендлеров.
  - удаеление из верстки.
- Вынести чувствительную информацию в отдельный проперти файл
  - информация вынесена в файл application-secret.yaml.
  - прописано использование переменных окружения + значения по умолчанию.
- Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL.
  - добавлена конфигурация application-test.yaml для тестов.
  - оптимизирован файл data.sql под использование с H2 (вместо postgres).
- Написать тесты для всех публичных методов контроллера ProfileRestController.
  - написаны тесты для двух публичных методов контроллера.
- Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный подход для работы с файловой системмой.
  - метод переписан с использованием NIO
- Написать Dockerfile для основного сервера.
  - добавлен файл Dockerfile.
- Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx. Для nginx используй конфиг-файл config/nginx.conf. При необходимости файл конфига можно редактировать.
  - добавлен файл docker-compose, запускающий БД, nginx и приложение.