
create database if not exists movies_db;

create table movies if not exists (
	id serial primary key,
	title varchar(45),
	description varchar(45),
	year int,
	votos int,
	rating float,
	image_url varchar(150)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) DEFAULT 'Bearer',
    revoked BOOLEAN NOT NULL,
    expired BOOLEAN NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
