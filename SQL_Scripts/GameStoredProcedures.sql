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
CREATE PROCEDURE create_new_player (IN PName varchar(255),In playerPlanet varchar(255),
			IN maxWeight int, in sName varchar(255))
this:BEGIN
	
	DECLARE alreadyPlayer varchar(255) DEFAULT NULL;
	
	SELECT name INTO alreadyPlayer
	FROM Person
	Where PName = name;
	
	
	if(alreadyPlayer IS not NULL) then
		leave this;
	end if;
	

	insert into Person
    (name,max_weight,employer,planet,shipName)
	values
	(pname,maxWeight,'User',playerPlanet,sName);
    
    insert into Inventory_entry
    (personName,goodName,quantity,weight)
    values
    (pname,'Money',50000,0);
    
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
	SELECT g.goodName, g.good_value, i.quantity, g.weight, g.description
	FROM Inventory_entry as i JOIN Goods AS g ON i.goodName = g.goodName
	WHERE personName = pname;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE make_trade (IN PName varchar(255), IN SName varchar(255), IN Good_Name varchar(255), IN QTY int)
this:BEGIN
	DECLARE value_buyer INT DEFAULT 0;
	DECLARE value_seller INT DEFAULT 0;
	DECLARE seller_qty INT DEFAULT 0;
    declare item_weight int default 0;
	declare oldQTY int default 0;
	
	SELECT quantity INTO value_buyer
	FROM Inventory_entry
	WHERE personName = PName AND 
		  goodName = 'Money';	
	
	SELECT good_value INTO value_seller
	FROM Goods
	WHERE goodName = Good_Name;
	
	SET value_seller = value_seller * QTY;
	
	
	IF value_buyer < value_seller
	THEN
		SELECT 'ERROR: NOT ENOUGH MONEY';
		leave this;
	END IF;
	
	SELECT quantity INTO seller_qty
	FROM Inventory_entry
	WHERE personName = SName AND 
		  goodName = Good_Name;
		  
	IF QTY > seller_qty
	THEN
		SELECT 'ERROR: VENDOR DOESNT HAVE ENOUGH STOCK';
		leave this;
	END IF;
	
	UPDATE Inventory_entry
	SET quantity = quantity - value_seller
	WHERE PName = personName AND 
		  goodName = 'Money'; 
	
	UPDATE Inventory_entry
	SET quantity = quantity - QTY
	WHERE goodName = Good_Name AND
		personName = SName; 
    
    select weight into item_weight
    from Inventory_entry
    where goodName = Good_Name AND
		personName = SName;
    
	select quantity into oldQTY
	from Inventory_entry
	where personName = PName and goodName = Good_Name;
	
	if(oldQTY >0) then
		update Inventory_entry
		set quantity = QTY+oldQTY where personName=PName and goodName=Good_Name;
        leave this;
	end if;
	insert Inventory_entry
	(personName,goodName,quantity,weight)
	values
	(PName,Good_Name,QTY,item_weight);
	
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE system_given_planet (IN planet_name varchar(255))
BEGIN
	SELECT star_system
	FROM Planet
	WHERE planet_name = name;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_player_money (IN pname varchar(255))
BEGIN
	SELECT quantity
	FROM Inventory_entry
	WHERE personName = pname and goodName='Money';
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_used_weight (IN PName varchar(255))
BEGIN
    select sum(quantity*weight) as used_weight
    from Inventory_entry
    where personName = PName;
	
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_player_ship (IN PName varchar(255))
BEGIN
    select shipName
    from Person
    where name = PName;
	
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_total_weight (IN PName varchar(255))
BEGIN
    select max_weight
    from Person
    where name = PName;
	
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_all_goods ()
BEGIN
    select goodName
    from Goods;
	
END//
DELIMITER ;

DELIMITER //
CREATE function player_exists (PName varchar(255)) returns boolean
begin
    DECLARE alreadyPlayer varchar(255) DEFAULT NULL;
	
	SELECT name INTO alreadyPlayer
	FROM Person
	Where PName = name and employer = "User";
	
	
	if(alreadyPlayer IS not NULL) then
		return true;
	end if;
    return false;
    
END//
DELIMITER ;

DELIMITER //
CREATE procedure drop_player (in PName varchar(255))
begin
    delete from Person
    where name = PName
    limit 1;
    
END//
DELIMITER ;

DELIMITER //
CREATE procedure give_good_to_player (in PName varchar(255), good_name varchar(255), QTY int)
this:begin
    
	DECLARE good varchar(255) DEFAULT NULL;
	DECLARE wgt int DEFAULT NULL;
	
	
	SELECT goodName INTO good
	FROM Inventory_entry
	Where PName = personName and goodName = good_name;
	
	
	
	IF (good IS NOT NULL)
	THEN
	
	Update Inventory_entry
	Set quantity = quantity + QTY
	WHERE personName = PName AND good_name = goodName;
	leave this;
	END IF;
	
	SELECT weight INTO wgt
	FROM Inventory_entry
	Where PName = personName and goodName = good_name;
	
	
	INSERT INTO Inventory_entry
	VALUES (PName, good_name, QTY, wgt);
		
END//
DELIMITER ;

DELIMITER //
CREATE procedure get_legality_good (in good_name varchar(255))
begin
    Select legality
	FROM Goods
	WHERE goodName = good_name;
END//
DELIMITER ;

DELIMITER //
CREATE procedure get_police_planet (in planet_name varchar(255))
begin
    SELECT police_level
	FROM Planet
	Where name = planet_name;
END//
DELIMITER ;

DELIMITER //
CREATE procedure get_police_star_system (in star_system_name varchar(255))
begin
    SELECT police_level
	FROM Star_System
	Where name = star_system_name;
END//
DELIMITER ;

DELIMITER //
CREATE procedure get_danger_planet (in planet_name varchar(255))
begin
    SELECT danger_level
	FROM Planet
	Where name = planet_name;
END//
DELIMITER ;

DELIMITER //
CREATE procedure get_danger_star_system (in star_system_name varchar(255))
begin
    SELECT danger_level
	FROM Star_System
	Where name = star_system_name;
END//
DELIMITER ;

DELIMITER //
CREATE function all_goods_illegal (PName varchar(255)) returns boolean
begin
    DECLARE Illegal varchar(255) DEFAULT NULL;
	
	SELECT name INTO Illegal
	FROM Inventory_entry as i JOIN Goods as g ON i.goodName = g.goodName
	Where PName != name AND legality = 1;
	
	
	if(Illegal IS NULL) then
		return true;
	end if;
    return false;
    
END//
DELIMITER ;



