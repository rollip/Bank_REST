# Запуск

## Требования к системе

Перед запуском убедитесь, что на вашей системе установлены:

* Docker
* Docker Compose
* Git

Клонировать репозиторий:

```bash
git clone https://github.com/rollip/Bank_REST.git
cd Bank_REST
```

Запустить приложение и базу данных через Docker Compose:

```bash
docker-compose up --build -d 
```

Приложение будет доступно по адресу:
Основной сервис: [http://localhost:8080](http://localhost:8080)
Swagger UI (документация API): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
