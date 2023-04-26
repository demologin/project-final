## [REST API](http://localhost:8080/doc)

## Concept:
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
- There are 2 common tables that don't have fk
  - _Reference_. We make the connection by _code_ (by id - is impossible, because id is tied to the environment-specific base)
  - _UserBelong_ - binding users with type (owner, lead, ...) to an object (task, project, sprint, ...). will check FK manually.

## Analogues
- https://java-source.net/open-source/issue-trackers

## Testing
- https://habr.com/ru/articles/259055/

Finished task:
TODO: Task 1. Learned structure of the project.
TODO: Task 2. Deleted social networks.
TODO: Task 3. Moved sensitive information to application-server.yaml file which takes info from environment variables
(should be used on prod server, change name to application-prod.yaml when deployed, use environment variables from the file).
TODO: Task 4. Partially ready. Changelog.xml ready. Two beans ready, commented. Changelog-h2.xml ready but fails the tests.
TODO: Task 5. Wrote tests for class ProfileRestController public methods.
TODO: Task 6. Adding tags to the tasks.