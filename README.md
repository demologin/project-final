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

### Use [startComposeClear.sh](scripts/startComposeClear.sh) for start docker-compose with db & nginx
### Use [stopComposeAndDeleteAppImage.sh](scripts/stopComposeAndDeleteAppImage.sh) for stop docker-compose and remove all data

## Выполненные задачи

1. ***Задача***: Разобраться со структурой проекта (onboarding).
    - ***Выполнено***:
        - Изучена структура проекта.
        - Выявлены основные модули и их связи.
        - Каждый модуль является изолированной частью приложения для уменьшения зависимостей между ними.

---
   
2. ***Задача***: Удалить социальные сети: vk, yandex.
    - ***Выполнено***:
        - Удалены классы обработчиков (handler's) для взаимодействия с VK и Yandex.
        - Создана и применена миграция Liquibase для удаления упоминаний VK и Yandex из БД.

---

3. ***Задача***: Вынести чувствительную информацию в отдельный проперти файл.
    - ***Выполнено***:
        - Чувствительная информация вынесена [application-secrets.yaml](src/main/resources/application-secrets.yaml)
        - application-secrets.yaml импортируется в основной файл.
        - Реализована система чтения проперти из переменных окружения при старте сервера.
        - Установлены дефолтные проперти для удобства при текущем ревью и проверке.

---

4. ***Задача***: Переделать тесты так, чтобы во время тестов использовалась in memory БД (H2), а не PostgreSQL.
    - ***Выполнено***:
        - Переделаны тесты, используется TestContainers.

---

5. ***Задача***: Написать тесты для всех публичных методов контроллера ProfileRestController.
    - ***Выполнено***:
        - Написаны тесты для проверки успешных и неуспешных сценариев.

---

6. ***Задача***: Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный подход для работы с файловой системмой.
    - ***Выполнено***:
        - Выполнен рефакторинг метода FileUtil#upload (используется MultipartFile, Path, Files).

---

7. ***Задача***: Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе).
    - ***Выполнено***:
        - Добавлен метод addTags в TaskController.
        - Добавлен метод addTagsToTask в TaskService.

---

8. ***Задача***: Добавить подсчет времени сколько задача находилась в работе и тестировании.
    - ***Выполнено***:
        - Добавлены два метода в сервис для вычисления времени в работе и в тестировании.
        - Добавлен метод getStatusChangeTime в ActivityRepository.
        - Создана и применена миграция Liquibase для добавления соответствующих записей в таблицу ACTIVITY.
        - EXTRA: добавлены тесты - [TaskServiceTest.java](src/test/java/com/javarush/jira/bugtracking/task/TaskServiceTest.java)

---

9. ***Задача***: Написать Dockerfile для основного сервера.
    - ***Выполнено***:
        - Написан [Dockerfile](Dockerfile) для основного сервера.

---

10. ***Задача***: Написать docker-compose файл для запуска контейнера сервера вместе с БД и nginx.
    - ***Выполнено***:
        - Написан [docker-compose.yml](docker-compose.yml) файл.
        - Сервер, БД и nginx запускаются в связанных контейнерах, используют две внутренних сети.
        - Используется файл переменных [docker-compose.env](config/docker-compose.env)
        - EXTRA: [bach скрипт](scripts/startComposeClear.sh) для старта docker-compose.
        - EXTRA: [bach скрипт](scripts/startComposeClear.sh) использует [HealthCheck](src/main/java/com/javarush/jira/util/HealthCheck.java) для ожидания старта приложения и автоматически открывает окно браузера при готовности nginx.

---

11. ***Задача***: Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы index.html.
    - ***Выполнено***:
        - Добавлена поддержка локализации для шаблонов писем и стартовой страницы, включая все "Thymeleaf fragment".
        - Реализована поддержка трех языков.

---

12. ***Задача***: Переделать механизм распознавания «свой-чужой» между фронтом и беком с JSESSIONID на JWT.
    - ***Выполнено***:
        - Не выполнено, и это меня гложет.