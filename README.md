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
- Task 1 - onboarding - use JDK17 - (need to change lombok dependency version to use jdk 21).
- Task 2 - delete vk, yandex
- Task 3 - add secure.yaml for sensitive properties using env variables (temp load values to System Parameters) - task 3
- Task 4 - add TestContainer, change jira-test db to test containers
- Task 5 - add tests for ProfileRestController
- Task 6 - refactor FileUtil#upload to nio
- Task 7 - add and delete task tags: REST API, service, front (in task and task-edit)
- Task 8 - add calculate task stages duration, methods: ActivityService - calculate task stages durations, TaskService - calculate task in work, task in testing
- Task 9 - add Dockerfile
- Task 10 - create docker-compose: app, nginx, db
- Task 11 - add ru, en, es,ba localization for index, mail 

Не выполнено:
- Task 12 - по отдельности все понятно, соединить пока не вышло - нужно больше практики