DROP DATABASE IF EXISTS noire_etoile;

CREATE DATABASE noire_etoile;

USE noire_etoile;

CREATE TABLE IF NOT EXISTS Employer(
name varchar(255),
status varchar(255),
PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS Person(
id int,
name varchar(255),
max_weight int,
employer varchar(255),
planet varchar(255),
PRIMARY KEY(id)
);



CREATE TABLE IF NOT EXISTS Star_log(
id int,
log_text varchar(512),
star_date int,
planet varchar(255),
star_system varchar(255),
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Inventory_entry(
person_id int,
good_id int,
quantity int,
weight int,
CONSTRAINT pk_person_good PRIMARY KEY (person_id, good_id)
);

CREATE TABLE IF NOT EXISTS Goods(
id int,
name varchar(255),
good_value int,
weight int,
legality boolean,
description varchar(512),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Produces(
employer_name varchar(255),
person_id int,
goods_id int,
quantity int,
CONSTRAINT pk_employer_person_goods PRIMARY KEY (employer_name, person_id, goods_id)
);

create TABLE IF NOT EXISTS Planet(
name varchar(255),
loc_x int,
loc_y int,
info varchar(512),
danger_level int,
police_level int,
star_system varchar(255),
PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS Star_System(
name varchar(255),
loc_x int,
loc_y int,
info varchar(512),
danger_level int,
police_level int,
PRIMARY KEY (name)
);

ALTER TABLE Person
ADD FOREIGN KEY (employer)
REFERENCES Employer(name)
ON delete CASCADE;

ALTER TABLE Person
ADD FOREIGN KEY (planet)
REFERENCES Planet(name)
ON delete CASCADE;

ALTER TABLE Star_log
ADD FOREIGN KEY (planet)
REFERENCES Planet(name)
ON delete CASCADE;

ALTER TABLE Star_log
ADD FOREIGN KEY (star_system)
REFERENCES Star_System(name)
ON delete CASCADE;

ALTER TABLE Inventory_entry
add FOREIGN KEY (person_id)
REFERENCES Person(id)
ON delete CASCADE;

ALTER TABLE Inventory_entry
ADD FOREIGN KEY (good_id)
REFERENCES Goods(id)
ON delete CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (employer_name)
REFERENCES Employer(name)
ON delete CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (person_id)
REFERENCES Person(id)
ON delete CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (goods_id)
REFERENCES Goods(id)
ON delete CASCADE;

ALTER TABLE Planet
ADD FOREIGN KEY (star_system)
REFERENCES Star_System(name)
ON delete CASCADE;

































  

