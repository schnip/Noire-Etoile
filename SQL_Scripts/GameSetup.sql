CREATE DATABASE ne_db;

use ne_db;
go;

CREATE TABLE Employer(
name varchar(255),
status varchar(255),
PRIMARY KEY(name)
);

CREATE TABLE Person(
id int,
name varchar(255),
max_weight int,
employer varchar(255),
planet varchar(255),
PRIMARY KEY(id),
);

CREATE TABLE Star_Log(
id int,
log_text varchar(512),
stardate int,
planet varchar(255),
star_system varchar(255),
PRIMARY KEY(id),
);

CREATE TABLE Inventory_entry(
person_id int,
good_id int,
quantity int,
weight int
CONSTRAINT pk_person_good PRIMARY KEY (person_id, good_id)
);

CREATE TABLE Goods(
id int,
name varchar(255),
good_value int,
weight int,
legality boolean,
description varchar(512),
PRIMARY KEY (id)
);

CREATE TABLE Produces(
employer_name varchar(255),
person_id int,
goods_id int,
quantity int,
CONSTRAINT pk_employer_person_goods PRIMARY KEY (employer_name, person_id, goods_id)
);

CREATE TABLE Planet(
name varchar(255),
loc_x int,
loc_y int,
info varchar(512),
danger_level int,
police_level int,
PRIMARY KEY (name)
);

CREATE TABLE Star_System(
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
DELETE ON CASCADE;

ALTER TABLE Person
ADD FOREIGN KEY (planet)
REFERENCES Planet(name)
DELETE ON CASCADE;

ALTER TABLE Star_Log
ADD FOREIGN KEY (planet)
REFERENCES Planet(name)
DELETE ON CASCADE;

ALTER TABLE Star_Log
ADD FOREIGN KEY (star_system)
REFERENCES Star_System(name)
DELETE ON CASCADE;

ALTER TABLE Inventory_entry
FOREIGN KEY (person_id)
REFERENCES Person(id)
DELETE ON CASCADE;

ALTER TABLE Inventory_entry
ADD FOREIGN KEY (good_id)
REFERENCES Goods(id)
DELETE ON CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (employer_name)
REFERENCES Employer(name)
DELETE ON CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (person_id)
REFERENCES Person(id)
DELETE ON CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (goods_id)
REFERENCES Goods(id)
DELETE ON CASCADE;

ALTER TABLE Planet
ADD FOREIGN KEY (star_system)
REFERENCES Star_System(name)
DELETE ON CASCADE;































  

