# first_userservice

## О проекте

Это приложение предоставляет REST API для управления пользователями и ролями.

Авторизация реализована по протоколу Oauth где роли клиента и владельца ресурса совмещены,

также совмещены роли сервера авторизации и сервера ресурсов.

Формат токена доступа - JWT.

Пользователь с ролью ROLE_ADMIN имеет польный доступ, пользователь с ролью ROLE_USER имеет права на просмотр.


## Как использовать
### Авторизация
| Операция | Метод запроса | Содержание запроса | Статус ответа | Содержание ответа |
|--|--|--|--|--|
| Получение токена доступа | POST | **Url**: '/login' <br/> **Body**: '{"login" : "some_login", "password" : "12345"}' | OK | **Header**:  "Authorization": "Bearer token"} |

### API пользователей
| Операция | Метод запроса | Содержание запроса | Статус ответа | Содержание ответа |
|--|--|--|--|--|
| Добавление пользователя | POST | **Url**: '/user' <br/> **Header** 'Authorization : bearer token' <br/> **Body**: '{"login" : "new_user", "password" : "123", "role" : { "name" : "ROLE_USER"}}' | CREATED | **Body**: {"id": 17, "login": "oleg123", "name": null, "surname": null, "role": {"id": 2, "name": "ROLE_USER"}} |
| Удаление пользователя | DELETE | **Url**: '/user/{user_id}' <br/> **Header** 'Authorization : bearer token' | OK | |
| Получение пользователя по id | GET | **Url**: '/user/{user_id}' <br/> **Header** 'Authorization : bearer token' | OK | **Body**: {"id": 17, "login": "oleg123", "name": null, "surname": null, "role": {"id": 2, "name": "ROLE_USER"}}|
| Получение всех пользователей | GET | **Url**: '/user' <br/> **Header** 'Authorization : bearer token' | OK | **Body** : [{"id": 17, "login": "oleg123", "name": null, "surname": null, "role": {"id": 2, "name": "ROLE_USER"}}]|
| Изменение пользователя | PATCH | **Url**: '/user' <br/> **Header** 'Authorization : bearer token' <br/> **Body** : '{"id" : "16", "name" : "New Name"}' | OK | |

### API ролей
| Операция | Метод запроса | Содержание запроса | Статус ответа | Содержание ответа |
|--|--|--|--|--|
| Добавление роли | POST | **Url**: '/role' <br/> **Header** 'Authorization : bearer token' <br/> **Body**: '{"name" : "ROLE_ANY"}' | CREATED | **Body**: {"id" : "12", name" : "ROLE_ANY"} |
| Удаление роли | DELETE | **Url**: '/role/{role_id}' <br/> **Header** 'Authorization : bearer token' | OK | |
| Получение роли по id | GET | **Url**: '/role/{role_id}' <br/> **Header** 'Authorization : bearer token' | OK | **Body**: {"id": 17, "name": "ROLE_ANY"}|
| Получение всех ролей | GET | **Url**: '/role' | OK | **Body** : [{"id": 17, "name": "ROLE_ADMIN"}, {"id": 18, "name": "ROLE_USER"}]|
| Изменение роли | PATCH | **Url**: '/role' <br/> **Header** 'Authorization : bearer token' <br/> **Body** : '{"id" : "17", "name" : "New Name"}' | OK | |

## Настройка и сборка

Настройки приложения содержатся в файле /src/main/resources/application.properties,

в котором необходимо указать параметры подключения к базе данных.

Также необхродимо сгенерировать новые RSA ключи для работы с JWT токенами.

Пароль для пользователя admin можно задать через файл /src/main/resources/db/migration/V1_2__insert_initial_data.sql,

предварительно сгенерировав его с помощью утилиты MakePass.

Сборка приложения осуществляется командой: mvn package.

Перед запуском приложения следует создать базу данных и настроить ее в соответсвии с файлом application.properties.


## Контакты

Email: kostasc@mail.ru
Telegram: @rkostashchuk