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

--next procedures that we need are as follows

--get a persons inventory based on a string for person_id
DELIMITER //
CREATE PROCEDURE persons_inventory (IN p_id int)
BEGIN
	SELECT good_id, quantity, weight
	FROM Inventory_entry
	WHERE person_id = p_id;
END//

--get all of the systems in the galaxy
DELIMITER //
CREATE PROCEDURE planets_given_system (IN star_system_name varchar(255))
BEGIN
	SELECT planet
	FROM Planet
	WHERE star_system = star_system_name;
END//
DELIMITER ;


--set the players planet person is sent and planet is sent
//DELIMITER //
CREATE PROCEDURE travel_to_planet (IN planet_name varchar (255), p_id int)
BEGIN
	UPDATE Person
	SET planet = planet_name
	WHERE p_id = id
END//
DELIMITER ;