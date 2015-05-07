if [ -e "SQL_Scripts/all.sql" ]
then
	rm SQL_Scripts/all.sql
fi
touch SQL_Scripts/all.sql
cat SQL_Scripts/GameSetup.sql >> SQL_Scripts/all.sql
cat SQL_Scripts/GameStoredProcedures.sql >> SQL_Scripts/all.sql
cat SQL_Scripts/GameData.sql >> SQL_Scripts/all.sql
mysql -u root -p < SQL_Scripts/all.sql 

