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

DELIMITER //
CREATE PROCEDURE get_vendors (IN planet_name varchar(255), pname varchar(255))
BEGIN
	SELECT name
	FROM Person
	WHERE planet_name = planet AND pname != name;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_goods_vendor (IN pname varchar(255))
BEGIN
	SELECT g.name, g.value, p.quantity, g.weight, g.description
	FROM InventoryEntry as i JOIN goods AS g ON i.good_id = g.id
	WHERE i.personName = pname;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE make_trade (IN buyer varchar(255), seller varchar(255), buyer_good_id int, quan_from_buyer int, seller_good_id int, quan_from_seller int)
BEGIN
	---FILL WITH SOMETHING COOL!
END//
DELIMITER ;




































