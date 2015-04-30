/* Inserting data into the data base */
USE noire_etoile;

INSERT INTO Planet
(name, loc_x, loc_y, info, danger_level, police_level)
VALUES
('omicron persei 8', 0, 0, "Lrrr doesn't like unwanted guests", 10, 10);

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

INSERT INTO Star_Log
(log_text, star_date, planet, star_system)
VALUES
('Eric was here but this was written by Josh',200,'Planet Eric',null);


