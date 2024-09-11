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
...
1. Удалить социальные сети: vk, yandex. 
    Удалено два класса в пакете src/main/java/com/javarush/jira/login/internal/sociallogin. 
    Удалены ссылка в ресурсных файлах html login и rgister.
    Удалены записи в файлах data.sql и changelog.sql
    Удалены регистрационные данные для полключения к сетям vk и yandex в файле application.yaml
2. Вынести чувствительную информацию в отдельный проперти файл: 
3. Добавлена локализация начальной страницы на русском и английском языках
4. Добавлена реализация посчета времени сколько задача находилась в статусах в
   работе и тестирование.
   добавлена функция REST API для вывода данной информации в секундах.
5. Выполнен рефакторин метода
   com.javarush.jira.bugtracking.attachment.FileUtil#upload в соотвествии с заданием.
6. Переделаны тесты для использования H2DB, исправлены сценарии sql в
   соотвествии с особенностями работы H2DB.
   Добавлены профили maven postgres и h2dbtest.
7. Добавлена возможность запуска в докер контейнерах:
   - для запуска приложения: необходимо выполнить start-app-dc.sh
   - создаются 3 контейнера nginx, db и app 
   - контейнеры nginx и app имеют доступ к внешней сети, а db - только к внутренней сети контейнеров
   - Не решенная задача: 
     - не получилось заставить nginx нормально работать в режиме обратного прокси, 
     из-за этого к контейнеру аpp сделана возможность непосредственного доступа из внешней сети по порту 8080   
