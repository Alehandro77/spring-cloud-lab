# Интернет-магазин (Микросервисная архитектура)

Учебный проект, демонстрирующий построение распределенной системы на базе **Spring Cloud**. 
Проект реализует паттерны микросервисной архитектуры: Database per Service, API Gateway, Service Discovery, Centralized Configuration и Circuit Breaker.

## Архитектура и сервисы

Система состоит из следующих компонентов:

| Сервис                | Порт | Назначение                                                         |
|:----------------------|:-----|:-------------------------------------------------------------------|
| **`config-server`**   | 8888 | Централизованный сервер конфигураций (Spring Cloud Config).        |
| **`eureka-server`**   | 8761 | Реестр сервисов для их обнаружения (Service Discovery).            |
| **`api-gateway`**     | 8080 | Единая точка входа (API Gateway), маршрутизация и Circuit Breaker. |
| **`auth-service`**    | 8085 | Регистрация, авторизация и выдача JWT-токенов.                     |
| **`product-service`** | 8081 | Управление каталогом товаров.                                      |
| **`order-service`**   | 8082 | Управление корзиной и заказами.                                    |
| **`admin-service`**   | 8086 | Админ панель.                                                      |

## Базы данных (Database per Service)

Для соблюдения изоляции данных каждый микросервис имеет свою собственную базу данных PostgreSQL и отдельного пользователя:

*   **Auth:** `auth-db` (Пользователь: `auth_user`, Пароль: `auth_pass`)
*   **Product:** `product-db` (Пользователь: `product_user`, Пароль: `product_pass`)
*   **Order:** `order-db` (Пользователь: `order_user`, Пароль: `order_pass`)

Схема таблиц (миграции) создается автоматически при старте сервисов с помощью **Flyway**.

## Инструкция по локальному запуску

### Шаг 1. Подготовка базы данных
Убедитесь, что у вас установлен PostgreSQL (версия 15+).
В корне проекта находится скрипт `scripts/init-db.sql`. Запустите его от имени суперпользователя `postgres`, чтобы создать базы и пользователей:

psql -U postgres -h localhost -f scripts/init-db.sql

### Шаг 2. Порядок запуска сервисов
Для корректной работы все сервисы должны запускаться строго в следующем порядке:

config-server (ожидаем Started ConfigServerApplication).

eureka-server (ожидаем Started EurekaServerApplication).

auth-service, product-service, order-service, admin-service (бизнес-сервисы).

api-gateway (шлюз, запускается последним).

### Шаг 3. Проверка работоспособности
Панель Eureka: http://localhost:8761 — все запущенные сервисы должны отображаться в статусе UP.

Swagger UI (Документация API):

Auth: http://localhost:8085/swagger-ui/index.html

Product: http://localhost:8081/swagger-ui/index.html

Order: http://localhost:8082/swagger-ui/index.html

Admin: http://localhost:8086/swagger-ui/index.html

### Авторизация (JWT)
Сервис auth-service реализует stateless-аутентификацию на основе JWT (JSON Web Token).
Пароли хэшируются с помощью BCrypt.

Как пользоваться (через API Gateway):

Регистрация: POST http://localhost:8080/api/auth/register (передаем email, password, firstName, lastName).

Логин: POST http://localhost:8080/api/auth/login (получаем JWT-токен в ответе).

Доступ к защищенным данным: В заголовках запроса передаем Authorization: Bearer <ваш_токен>.

### Отказоустойчивость (Circuit Breaker)
В api-gateway настроен паттерн Circuit Breaker (библиотека Resilience4j).
Если один из сервисов (например, auth-service) падает или не отвечает, Gateway не выдает ошибку 500 или бесконечное ожидание. Вместо этого срабатывает FallbackController, который мгновенно возвращает пользователю понятный JSON-ответ со статусом 503 Service Unavailable.

### Стек технологий
Java 17

Spring Boot 3.3.0

Spring Cloud (Config, Eureka, Gateway, OpenFeign)

Spring Security + JWT (jjwt)

Spring Data JPA + Hibernate

PostgreSQL + Flyway (миграции)

Springdoc OpenAPI (Swagger UI)

Resilience4j (Circuit Breaker)
```bash