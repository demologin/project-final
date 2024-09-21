## Важно для проверки:

## Как запустить приложение и тесты из среды разработки:
  - Требуется закомментировать строку `spring.datasource.url=jdbc:postgresql://postgres-db:5432/jira`
    и раскомментировать строку `spring.datasource.url=jdbc:postgresql://localhost:5432/jira`
    в файле src/main/resources/application.properties
  - Удалить контейнеры, образы , тома (volumes) созданные при запуске docker compose
  - Создать контейнер с бд jira в терминале/консоли:
    `docker run -p 5432:5432 --name postgres-db -e POSTGRES_USER=jira -e POSTGRES_PASSWORD=JiraRush -e POSTGRES_DB=jira -e 
    PGDATA=/var/lib/postgresql/data/pgdata -v ./pgdata:/var/lib/postgresql/data -d postgres`
  - Создать и заполнить таблицы бд jira c помощью скриптов `db/changelog.sql`, `data4dev/data.sql`
  - ВЫполнить метод main() класса com.javarush.jira.JiraRushApplication (для запуска приложения и отслеживания
    его работы по адресу http://localhost:8080/)
  - Запустить тесты из контекстного меню командой Run 'All Tests' директории src/test/java

## Как запустить приложение с помощью docker-compose:
  - Требуется закомментировать строку `spring.datasource.url=jdbc:postgresql://localhost:5432/jira`
    и раскомментировать строку `spring.datasource.url=jdbc:postgresql://postgres-db:5432/jira`
    в файле src/main/resources/application.properties
  - Удалить docker контейнер с бд jira `postgres-db` и другие контейнеры, образы, тома ,
    которые могли появиться при предыдущем выполнении команды `docker-compose up --build`
  - Выполнить команду maven `mvn clean install package -Dmaven.test.skip=true`
  - Выполнить команду в терминале/консоли `docker-compose up --build` 
    При выполнении команды на линукс иногда появлялось сообщение `failed to solve: error from sender: open 
    /home/miux/Java/javarush/project-final/pgdata-test/pgdata: permission denied`. Для устранения проблемы приходилось
    переназначать права директории `./pgdata` на чтение и запись. Повторно вызывать команду `docker-compose up --build`
  - Для полноценной проверки приложения требуется заполнить созданные таблицы бд jira, сxема public (сейчас она находится в контейнере созданном docker compose)
    с помощью скрипта `data4dev/data.sql`.

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

## Список выполненных задач:

1, 2, 3, 4, 6, 9, 10
застрял на 5-й
...