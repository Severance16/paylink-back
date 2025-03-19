# 🌐 Backend - Spring Boot

Este proyecto es un backend desarrollado con **Spring Boot**, que consume una API externa y además implementa sus propios métodos para almacenar datos en una base de datos **PostgreSQL**.

## 🚀 Características

- 📡 Consumo de una API externa para obtener información relevante.
- 🗄️ Gestión y almacenamiento de datos en PostgreSQL.
- 📑 Documentación de la API con Swagger.

## 🛠️ Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring WebClient (para consumo de API externa)
- Spring Data JPA
- PostgreSQL

## 📦 Instalación y Configuración

### Requisitos Previos

- Tener **Java 21** instalado ([descargar aquí](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)).
- Tener **Maven** instalado.
- Tener **PostgreSQL** configurado y en ejecución.

### Configuración de Variables de Entorno

Este proyecto usa un archivo **.env** para manejar configuraciones sensibles. Renombra el archivo `.env.example` a `.env` y edita las siguientes variables:

```env
    API_URL=Endpoint del API de punto red
    API_KEY=API key para consumir el API
    DB_USER=Usuario de la base de datos
    DB_PASSWORD=Contraseña del usuario de la base de datos
    DB_HOST=Host de la base de datos
    DB_PORT=Puerto de la base de datos
    DB_NAME=Nombre de la base de datos a usar
```

### Pasos de Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/Severance16/paylink-back.git
   ```
2. Ingresa al directorio del proyecto:
   ```sh
   cd paylink-back
   ```
3. Compila el proyecto con Maven:
   ```sh
   mvn clean install
   ```
4. Ejecuta el backend:
   ```sh
   mvn spring-boot:run
   ```

## 📄 Documentación de la API

Este backend cuenta con **Swagger**. Para ver la documentación, inicia el backend y accede a:
```
http://localhost:8080/swagger-ui/index.html
```


