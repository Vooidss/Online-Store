# Интернет-магазин

## Описание проекта

Данный проект представляет собой полнофункциональное веб-приложение интернет-магазина. Он обеспечивает пользовательский интерфейс для просмотра каталога товаров, фильтрации по категориям или другим параметрам, добавления товаров в корзину и оформления заказа. Пользователи могут регистрироваться и входить в систему, а после формирования заказа – переходить к оплате. Backend приложения реализован на Java с использованием Spring Boot и сопутствующих технологий, а frontend – на React. Проект также демонстрирует применение микросервисного подхода и асинхронного взаимодействия сервисов (через Apache Kafka), а для удобства развёртывания используется контейнеризация (Docker) с оркестрацией через Docker Compose.

## Используемые технологии

**Backend:**

* **Spring Boot** – фреймворк для быстрой конфигурации и запуска веб-приложения на Java.
* **Spring MVC** – модуль для реализации шаблона Model-View-Controller, используется для построения RESTful API (контроллеры и обработка HTTP-запросов).
* **Spring Data JPA** – модуль для упрощённой работы с базой данных через ORM Hibernate (генерация запросов, репозитории).
* **Spring Security** – система аутентификации и авторизации пользователей, защита веб-ресурсов приложения.
* **Spring Cloud** – набор инструментов для разработки распределённых систем и микросервисов (возможное использование Discovery Client, Config Server, etc.).
* **Hibernate** – ORM-фреймворк для отображения Java объектов в реляционную базу данных и выполнения запросов.
* **Apache Kafka** – распределённая система обмена сообщениями, используется для асинхронного обмена событиями между сервисами (например, обработка событий заказа, уведомления и т.д.).
* **Flyway** – инструмент для управления версионными миграциями базы данных, автоматически применяет SQL-скрипты для создания/обновления схемы при запуске приложения.

**Frontend:**

* **React** – JavaScript библиотека для создания пользовательского интерфейса SPA (Single Page Application).
* **JavaScript (ES6+)** – язык программирования, используемый в React-приложении для динамической работы на стороне клиента (в браузере).

**DevOps и прочее:**

* **Docker** – контейнеризация приложения; каждый сервис (frontend, backend, БД, Kafka и др.) упакован в контейнер для удобства деплоя.
* **Docker Compose** – инструмент оркестрации многоконтейнерного окружения; используется единый YAML-файл для запуска всех компонентов проекта командой одной командой.

## Основная функциональность

* **Регистрация и авторизация пользователей:** возможность создать новую учётную запись, вход в систему под своими учетными данными.
* **Каталог товаров и фильтрация:** отображение списка товаров с возможностью фильтрации по категориям, ценовому диапазону и другим параметрам для удобного поиска нужных позиций.
* **Добавление товаров в корзину:** выбор товаров и добавление их в корзину покупок для последующего оформления заказа.
* **Оформление заказов:** заполнение необходимой информации (адрес доставки, контакты и пр.), расчет итоговой стоимости и подтверждение заказа.
* **Оплата заказов:** интеграция с платёжным модулем или внешним сервисом для проведения оплаты (в рамках учебного проекта может быть эмуляция успешной оплаты).
* **Уведомления и обработка событий:** после оформления и оплаты заказа система может генерировать события (через Kafka) для других сервисов, например, обновление складских остатков, уведомление службы доставки и т.п. *(при наличии соответствующих сервисов)*.

## Требования к окружению

Для запуска приложения потребуется следующее окружение:

* **Docker** – установленный Docker Engine (версия 20.x и выше).
* **Docker Compose** – установленный инструмент Docker Compose (v2 или отдельный docker-compose, в зависимости от вашей установки Docker).

При использовании Docker Compose нет необходимости отдельно устанавливать Java или Node.js локально – все необходимые зависимости упакованы в Docker-образы. Однако, при желании запустить компоненты вне Docker, потребуются: JDK (Java 17+), Maven/Gradle для сборки бекенда, а также Node.js (например, v14+ или v16+) и npm/Yarn для фронтенда.

**Примечание:** Убедитесь, что порты, используемые приложением, свободны на вашем хосте. По умолчанию фронтенд (React) может быть доступен на порту **3000**, бекенд (Spring Boot) – на порту **8080**, база данных (если используется, например PostgreSQL) – на порту **5432**, Kafka – на порту **9092** и т.д. Эти порты можно изменить в конфигурации Docker Compose при необходимости.

## Инструкция по запуску с Docker Compose

1. **Клонирование репозитория.** Склонируйте код проекта на свой компьютер. Если используется Git:

   ```bash
   git clone <URL-репозитория>
   cd <директория проекта>
   ```
2. **Конфигурация окружения (при необходимости).** При необходимости отредактируйте настройки в файлах конфигурации. Например, это могут быть файлы `.env` или параметры в `application.yml` для бекенда (настройки БД, Kafka, портов и проч.). По умолчанию предоставлены стандартные настройки, подходящие для локального запуска.
3. **Запуск Docker Compose.** Выполните команду в корневой директории проекта, где расположен файл `docker-compose.yml`:

   ```bash
   docker-compose up --build -d
   ```

   Флаг `--build` заставит пересобрать образы (полезно, если вы внесли изменения в код), а `-d` запустит контейнеры в фоновом режиме. Первый запуск может занять несколько минут, так как Docker загрузит необходимыe образы (например, образ базы данных, Kafka и т.д.) и соберёт образы для вашего кода.
4. **Ожидание старта сервисов.** После выполнения команды Docker Compose поднимет все указанные сервисы. Подождите некоторое время (несколько секунд до минуты) пока все контейнеры и внутренние службы инициализируются. Вы можете мониторить логи с помощью команды `docker-compose logs -f` для отслеживания процесса запуска (особенно полезно убедиться, что backend подключился к базе данных, выполнил миграции Flyway, а Kafka успешно запущена).
5. **Доступ к приложению.** После успешного запуска окружения убедитесь, что фронтенд и бекенд доступны:

    * Откройте в браузере фронтенд-приложение по адресу: **`http://localhost:3000`**. Вы должны увидеть главную страницу интернет-магазина (каталог товаров).
    * Бекенд-приложение (API) будет доступно по адресу **`http://localhost:8080`** (если нужно проверить его отдельно, например, через Swagger UI или прямые запросы). Фронтенд уже настроен обращаться к API бекенда автоматически (URL можно проверить/изменить в настройках фронтенда, например в файле конфигурации или переменных окружения React).
6. **Тестирование функциональности.** Теперь можно зарегистрировать нового пользователя через интерфейс, войти под его учетной записью и провести тестовый сценарий: добавить товары в корзину, оформить заказ и имитировать его оплату. Убедитесь, что соответствующие действия проходят успешно (данные сохраняются, изменения отображаются на UI, и т.д.). При возникновении ошибок проверяйте логи контейнеров (`docker-compose logs`) для устранения проблем.
7. **Остановка приложения.** Когда вы завершили работу, остановить и удалить запущенные контейнеры можно командой:

   ```bash
   docker-compose down
   ```

   Эта команда остановит все сервисы, связанные с текущим составом Docker Compose, и удалит контейнеры (данные в том числе будут удалены, за исключением случаев, если указаны тома для сохранения, например для БД).

### Состав контейнеров

| Сервис         | Образ                      | Порт (хост → контейнер) | Описание                                         |
|----------------|----------------------------|-------------------------|--------------------------------------------------|
| **zookeeper**  | `zookeeper:3.7.0`          | `2181 → 2181`           | Сервис ZooKeeper для координации Kafka           |
| **kafka**      | `obsidiandynamics/kafka`   | `9092 → 9092`           | Брокер Kafka                                     |
| **kafdrop**    | `obsidiandynamics/kafdrop` | `9000 → 9000`           | Web-интерфейс для мониторинга Kafka              |
| **postgres**   | `postgres:16`              | `5432 → 5432`           | PostgreSQL с БД `OnlineStore`                    |
| **pgadmin**    | `dpage/pgadmin4:latest`    | `5433 → 80`             | Web-интерфейс pgAdmin для управления PostgreSQL   |
| **discovery**  | сборка из `backend/discovery` | `8761 → 8761`           | Сервис Eureka Discovery                          |
| **gateway**    | сборка из `backend/gateway`   | `8091 → 8091`           | API Gateway                                      |
| **config-server** | сборка из `backend/config-server` | `8888 → 8888`           | Spring Cloud Config Server                       |
| **authorization** | сборка из `backend/authorization`  | `8060 → 8060`           | Сервис аутентификации и генерации JWT            |
| **basket**     | сборка из `backend/basket`       | `8050 → 8050`           | Сервис корзины                                   |
| **orders**     | сборка из `backend/orders`       | `8020 → 8020`           | Сервис заказов                                   |
| **products**   | сборка из `backend/products`     | `8071 → 8071`           | Сервис управления каталогом товаров              |


---


## Структура проекта

Структура репозитория организована следующим образом (основные директории и файлы):

```
project-root/
├── backend/                   # Spring Boot приложение (серверная часть)
│   ├── src/                   # Исходный код backend (Java, папки: controllers, services, models, repositories, конфигурации)
│   ├── pom.xml                # Maven файл проекта (зависимости и сборка) или build.gradle для Gradle
│   └── ...                    # Прочие файлы и директории бекенда (Flyway миграции в resources/db/migration, настройки в application.properties и т.д.)
├── frontend/                  # React приложение (клиентская часть)
│   ├── src/                   # Исходный код frontend (компоненты React, маршруты, Redux-состояние или контекст, стили и др.)
│   ├── public/                # Статические файлы и шаблон HTML для React
│   ├── package.json           # Файл npm зависимостей и скриптов для сборки/запуска фронтенда
│   └── ...                    # Прочие файлы фронтенда (например, конфигурации Webpack/Vite, .env файлы)
├── docker-compose.yml         # Конфигурация Docker Compose для запуска всех компонентов приложения
└── README.md                  # Документация проекта (настоящий файл)
```

*Примечание:* В реальном проекте названия файлов/директорий могут отличаться. Например, Dockerfile-ы могут называться иначе или располагаться внутри соответствующих модулей (`backend/Dockerfile`, `frontend/Dockerfile`). Структура выше дана для общего понимания организации кода.
