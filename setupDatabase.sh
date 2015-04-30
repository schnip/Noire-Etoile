mysql -u root -p < SQL_Scripts/GameSetup.sql 
mysql -u user -p < SQL_Scripts/GameStoredProcedures.sql
mysql -u user -p < SQL_Scripts/GameData.sql
