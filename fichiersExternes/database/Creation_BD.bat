REM SCRIPT CREATION_BD.bat
REM Auteur : Vincent Renaud
REM Date de création : 26/12/2020

cd /
cd "Program Files\MySQL\MySQL Server 8.0\bin"
REM {{votre mot de pass root à la ligne de la commande ci-dessous}}
mysql -u root -p

CREATE USER IF NOT EXISTS 'Analyse'@'localhost' IDENTIFIED BY 'AnalysePW'; 
GRANT ALL PRIVILEGES ON *.* TO 'Analyse'@'localhost';

exit
mysql -u Analyse -p
AnalysePW

create database if not exists analyse;
use analyse;

drop table if exists fraudeur;

CREATE TABLE `fraudeur` (
  `id_compagnie` VARCHAR(7) NOT NULL,
  `nbr_facture` INTEGER NOT NULL,
  `nbr_facture_duplique` INTEGER NOT NULL,
  `pourcentage_dupli` DECIMAL(17,14) NOT NULL,
  PRIMARY KEY (`id_compagnie`)
);

