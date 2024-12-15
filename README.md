

# Clonar el repositorio

- Entrar en la carpeta de trabajo

# Prerequisitos tener instalado:

- Java 17
- mvn 3.9.8

## Levantar base de datos postgres en un container docker

```
docker-compose up -d
```

## conectarse con la base de datos con dbearer o tu tools favorita para conectar a postgres

```
Host: localhost
Database: movies_db
Port: 5436
Username: postgres
password: postgres1234
```


## crear tablas movies, user y token en la database movies_db revisar el archivo create_tables.sql

src\db\create_tables.sql


## cambia el nombre del archivo .env.example a .env



###  Limpiar y compilar la applicación

```
mvn clean install
```

### Ejecutar la applicacion

```
mvn spring-boot:run
```
