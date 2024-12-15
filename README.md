# Proyecto Backend con Spring Boot y PostgreSQL

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## Descripción

Este proyecto es un backend desarrollado con **Spring Boot**, un potente framework de Java que permite construir aplicaciones de manera rápida y sencilla, y utiliza **PostgreSQL** como sistema de gestión de bases de datos relacional.

### Características principales

- **Framework**: Spring Boot
- **Base de datos**: PostgreSQL
- **Gestión de dependencias**: Maven
- **Versión de Java**: 17
- **ORM**: Hibernate (JPA)

## Requisitos previos

Antes de ejecutar este proyecto, asegúrate de tener instalados los siguientes programas:

- Java 17 o superior
- Maven 3.8 o superior
- Docker Desktop

## Clonar el repositorio

1. Clonar este repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-backend.git
   ```

2. Entrar en la carpeta de trabajo:
   ```bash
   cd proyecto-backend
   ```

## Levantar base de datos PostgreSQL en un contenedor Docker

1. Ejecutar el siguiente comando para levantar la base de datos:
   ```bash
   docker-compose up -d
   ```

2. Conéctate con la base de datos usando DBeaver o tu herramienta favorita:
   ```
   Host: localhost
   Database: movies_db
   Port: 5436
   Username: postgres
   Password: postgres1234
   ```

## Configuración de la base de datos

1. Crear las tablas `movies`, `user` y `token` en la base de datos `movies_db`.
   Revisa el archivo `create_tables.sql` ubicado en:
   ```
   src\db\create_tables.sql
   ```

2. Cambiar el nombre del archivo `.env.example` a `.env` y configurar las variables necesarias.

## Configuración del proyecto

1. Limpiar y compilar la aplicación:
   ```bash
   mvn clean install
   ```

2. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Estructura del proyecto

El proyecto sigue una arquitectura estándar en Spring Boot:

- **Controller**: Maneja las solicitudes HTTP.
- **Service**: Contiene la lógica de negocio.
- **Repository**: Interactúa con la base de datos utilizando JPA/Hibernate.
- **Entity**: Define las entidades del modelo de datos.

## Contribuciones

¡Las contribuciones son bienvenidas! Por favor, abre un issue o crea un pull request para sugerir mejoras.

## Licencia

Este proyecto está licenciado bajo la [MIT License](LICENSE).