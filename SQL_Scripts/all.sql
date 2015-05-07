DROP DATABASE IF EXISTS noire_etoile;

CREATE DATABASE noire_etoile;

USE noire_etoile;

CREATE TABLE IF NOT EXISTS Employer(
name varchar(255),
status varchar(255),
PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS Person(
name varchar(255) unique,
max_weight int,
employer varchar(255),
planet varchar(255),
PRIMARY KEY(name)
);



CREATE TABLE IF NOT EXISTS Star_log(
id int NOT NULL AUTO_INCREMENT,
log_text varchar(512),
star_date int,
planet varchar(255),
star_system varchar(255),
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Inventory_entry(
personName varchar(255),
good_id int,
quantity int,
weight int,
CONSTRAINT pk_person_good PRIMARY KEY (personName, good_id)
);

CREATE TABLE IF NOT EXISTS Goods(
id int NOT NULL AUTO_INCREMENT,
name varchar(255),
good_value int,
weight int,
legality boolean,
description varchar(512),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Produces(
employer_name varchar(255),
personName varchar(255),
goods_id int,
quantity int,
CONSTRAINT pk_employer_person_goods PRIMARY KEY (employer_name, personName, goods_id)
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
add FOREIGN KEY (personName)
REFERENCES Person(name)
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
ADD FOREIGN KEY (personName)
REFERENCES Person(name)
ON delete CASCADE;

ALTER TABLE Produces
ADD FOREIGN KEY (goods_id)
REFERENCES Goods(id)
ON delete CASCADE;

ALTER TABLE Planet
ADD FOREIGN KEY (star_system)
REFERENCES Star_System(name)
ON delete CASCADE;

































  

/* Stored Procedures
	-News for a given planet 
	-- Takes Planet, returns top 20, by date
	-Same for star system, Overall
	-listing names of planets for a given system
*/
USE noire_etoile;

DELIMITER //
CREATE PROCEDURE log_from_planet (IN planet_name varchar(255))
BEGIN
	SELECT log_text, star_date
	FROM Star_log
	WHERE planet_name = planet;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE log_from_star_system (IN star_system_name varchar(255))
BEGIN
	SELECT log_text, star_date
	FROM Star_log
	WHERE star_system_name = star_system AND planet = NULL;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE log_from_Galaxy()
BEGIN
	SELECT planet, star_system, log_text, star_date
	FROM Star_log
	WHERE planet = NULL AND star_system = NULL;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE persons_inventory (IN pname varchar(255))
BEGIN
	SELECT good_id, quantity, weight
	FROM Inventory_entry
	WHERE personName = pname;
END//

DELIMITER //
CREATE PROCEDURE planets_given_system (IN star_system_name varchar(255))
BEGIN
	SELECT name
	FROM Planet
	WHERE star_system = star_system_name;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE all_systems_in_galaxy ()
BEGIN
	SELECT name
	FROM Star_System;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE travel_to_planet (IN planet_name varchar (255),IN pname varchar(255))
BEGIN
	UPDATE Person
	SET planet = planet_name
	WHERE pname = name;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE player_planet (IN pname varchar(255))
BEGIN
	select planet
	from Person
	WHERE pname = name;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE create_new_player (IN pname varchar(255),In playerPlanet varchar(255),
			IN maxWeight int)
BEGIN
	insert into Person
    (name,max_weight,employer,planet)
	values
	(pname,maxWeight,'System',playerPlanet);
END//
DELIMITER ;
/* Inserting data into the data base */
USE noire_etoile;

INSERT INTO Star_System
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Easter Egg Nebula', 0,0, "Check out this!", 66, 66);

INSERT INTO Star_System
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Larry is going crazy', 30,50, "He is freaking out!", 40, 20);

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level,star_system)
VALUES
('Omicron Persei 8', 0, 0, "Lrrr doesn't like unwanted guests", 10, 10,'Easter Egg Nebula');

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level,star_system)
VALUES
('Tatooine', 10, 10, "Maybe vader some day later now he's just a small fry", 2, 2,'Larry is going crazy');

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level,star_system)
VALUES
('Sriram Land', 10, 10, "The best place in the galaxy", 2, 2,'Larry is going crazy');

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level,star_system)
VALUES
('Planet Eric', 20, 20, "You fools! HAHAHA", 100, 100,'Easter Egg Nebula');

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Josh',200,'Planet Eric',null);

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Schnipke',201,'Planet Eric',null);

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Pie',210,'Planet Eric',null);

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Banana',195,'Planet Eric',null);

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Grapes',215,'Planet Eric',null);

INSERT INTO Star_log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Cherry',202,'Planet Eric',null);

insert into Employer
(name,status)
values
('System','Impartial');

insert into Employer
(name,status)
values
('Itune','Good');

insert into Employer
(name,status)
values
('Vamefi','Good');

insert into Employer
(name,status)
values
('Triad','Bad');

insert into Person
(name,max_weight,employer,planet)
values
('Planet Eric Cargo Hold',999999,'System','Planet Eric');

insert into Person
(name,max_weight,employer,planet)
values
('O.P.8 Ship Depo',999999,'System','Omicron Persei 8');

insert into Person
(name,max_weight,employer,planet)
values
('Covvuw',2500,'Itune','Planet Eric');

insert into Person
(name,max_weight,employer,planet)
values
('Hook',2500,'Triad','Omicron Persei 8');

insert into Goods
(name,good_value,weight,legality,description)
values
('Thuster',500,100,true,'You might need these to get off of the groud.');

insert into Goods
(name,good_value,weight,legality,description)
values
('Columbian Powdered Suger',1000,10,false,'If you know what I mean. (nudge nudge)');

insert into Inventory_entry
(personName,good_id,quantity,weight)
values
('Hook',2,50,500);

insert into Inventory_entry
(personName,good_id,quantity,weight)
values
('Planet Eric Cargo Hold',1,5,500);

insert into Goods
(name,good_value,weight,legality,description)
values
('Fixer-up Engine',500,500,true,'It\'ll at least keep your ship running... at least for a little while');

insert into Goods
(name,good_value,weight,legality,description)
values
('Credit',1,0,true,'The currency of the Galaxy');

insert into Inventory_entry
(personName,good_id,quantity,weight)
values
('Planet Eric Cargo Hold',3,1,500);

insert into Goods
(name,good_value,weight,legality,description)
values
('StarFleet v500 Deluxe',1500,750,true,'A great option for a Galactic Traveler on a Budget');

insert into Inventory_entry
(personName,good_id,quantity,weight)
values
('O.P.8 Ship Depo',4,1,750);