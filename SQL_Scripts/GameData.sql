/* Inserting data into the data base */
USE noire_etoile;

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Omicron Persei 8', 0, 0, "Lrrr doesn't like unwanted guests", 10, 10);

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Tatooine', 10, 10, "Maybe vader some day later now he's just a small fry", 2, 2);

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Planet Eric', 20, 20, "You fools! HAHAHA", 100, 100);

INSERT INTO Star_System
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('Easter Egg Nebula', 0,0, "Check out this!", 66, 66);

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

insert into Inventory_Entry
(person_id,good_id,quantity,weight)
values
('Hook','Columbian Powdered Suger',50,500);

insert into Inventory_Entry
(person_id,good_id,quantity,weight)
values
('Planet Eric Cargo Hold','Thruster',5,500);

insert into Goods
(name,good_value,weight,legality,description)
values
('Fixer-up Engine',500,500,true,'It\'ll at least keep your ship running... at least for a little while');

insert into Goods
(name,good_value,weight,legality,description)
values
('Credit',1,0,true,'The currency of the Galaxy');

insert into Inventory_Entry
(person_id,good_id,quantity,weight)
values
('Planet Eric Cargo Hold','Fixer-up Engine',1,500);

insert into Goods
(name,good_value,weight,legality,description)
values
('StarFleet v500 Deluxe',1500,750,true,'A great option for a Galactic Traveler on a Budget');

insert into Inventory_Entry
(person_id,good_id,quantity,weight)
values
('O.P.8 Ship Depo','StarFleet v500 Deluxe',1,750);