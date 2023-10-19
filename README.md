# tenpo-challenge

# Tabla de Contenidos

1. [Breve Descripción](#Breve Descripción)
2. [Stack Tecnologías](#Stack Tecnologías)
3. [Setup Local](#Setup Local)
4. [Documentación](#Documentación)

## Breve Descripción
Microservicio desarollado con Java 21 y Spring Boot 3 que utiliza maven como gestor de dependencias.

Utiliza Spring Data para almacenamiento en Base de Datos Postgres SQL, y cuenta con chache en redis.

Tambien esta configurado para levantar en un entorno de contenedores con docker.

Y cuenta con Documentación de Open Api.


## Stack Tecnologías

- _Lenguaje:_ `Java <21>`
- _Framework:_ `Springboot <3.1.4>`
- _Postgres SQL:_  `13.1`
- _Spring Data Redis:_  `3.1.4`
- _Maven:_  `3.3.9+`
- _Open Api_ `2.2.0`


## Setup Local

### _Dependencias_

Para iniciar el microservicio de forma local es necesario contar con las siguientes dependencias

* `Postgres SQL`: Contar con una db postgress operativa, y configurar las siguentes variables de entonrno: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME y SPRING_DATASOURCE_PASSWORD. 
  Para ejemplo de valores ver 'Variables de Entorno'

* `Redis` : Contar con una conexion a redis y configurar las siguentes variables de entorno: SPRING_DATA_REDIS_HOST, SPRING_DATA_REDIS_PORT y SPRING_DATA_REDIS_TIMEOUT.
  Para ejemplo de valores ver 'Variables de Entorno'

### _Variables de Entorno_

Tambien es necesario confiigurar las variables de entorno requeridas por el microservicio, a modo de ejemplo se deja el siguente setup, que debe ser cambiado a propia necesidad, y teniendo en cuenta el punto anterior:

    EXTERNAL_SERVICE_URL=http://localhost:8080;EXTERNAL_SERVICE_URN=/percentage;SPRING_APPLICATION_ASYNC_MAX_POOL_SIZE=10;SPRING_APPLICATION_ASYNC_POOL_SIZE=5;SPRING_APPLICATION_ASYNC_QUEUE_SIZE=25;SPRING_APPLICATION_CONNECTION_TIME_OUT=60;SPRING_APPLICATION_READ_TIME_OUT=60;SPRING_DATA_REDIS_HOST=localhost;SPRING_DATA_REDIS_PORT=6379;SPRING_DATA_REDIS_TIMEOUT=6000;SPRING_DATASOURCE_PASSWORD=postgres;SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres;SPRING_DATASOURCE_USERNAME=postgres;SPRING_RETRY_CONFIG_BACK_OF_PERIOD=1000;SPRING_RETRY_CONFIG_MAX_ATTEMPTS=3;SPRING_APPLICATION_MAX_REQUEST_SECONDS_INTERVAL=1;SPRING_APPLICATION_MAX_REQUEST_COUNT=3

### _Iniciar el proyecto_

Una vez que se cuenta con las 'Dependencias' y 'Variables de entorno', el proyecto se puede ejecutar desde un IDE o una terminal.

* `IDE`: Para iniciar desde un IDE solo basta con abrir el proyecto maven para que se descargen las depencias y luego dar start o debug
* `Terminal` : Para iniciar desde una terminal se debe acceder a la raiz del proyecto '../tenpo-challenge' y ejecutar los siguientes comandos:

```bash
    mvn clean install 
    mvn spring-boot:run
```
Tener en cuenta que para que el comando anterior funcione debemos tener configuradas las variables de entorno a nivel local, o tambien se pueden enviar los valores dentro de
los arguments que recibe spring.
Ejemplo : 
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:postgresql://localhost:5432/postgres,--spring.datasource.username=postgres,--spring.datasource.password=postgres
```

### _Iniciar el proyecto con Docker_

Como requisito previo se requiere la instalación de [docker](https://docs.docker.com/install/).

Luego para iniciar con Docker, solo basta con ejecutar el siguiente comandos desde la raíz del proyecto:

```bash
    docker-compose up  
```
Asegurarse que las imagenes de docker se descarguen de forma exitosa

## Documentación

A continuación se presentan dos alternativas para acceder a la documentación de la API
* Swagger : Por medio de un navegador accerder a la url `{host}/tenpo/api-rest/docs`. Ej: http://localhost:9009/tenpo/api-rest/docs
* Postman : Importar la collection de Postman mediante la url : `{host}/tenpo/api-rest/api-docs`. Ej: http://localhost:9009/tenpo/api-rest/api-docs