
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