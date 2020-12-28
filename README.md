# Bienvenue sur Analyse_Application

Ce projet est une application graphique permettant le chargement de données récupérées depuis un fichier.csv dans une collection synchronisée . Les données sont ensuite analysées en arrière plan pour permettre de ne pas bloquer l'utilisateur lors de ses requêtes. Elles sont finalement affichées une fois que l'analyse est terminée et que le bouton d'affichage est appuyé.

## Mise en place de l'environnement

1. **Utilisation de l'IDE IntelliJ**
    * **openjdk-15** "java version 15.0.1"
    
	* **Gradle**
	
	  * fichier **build.gradle**
         >   plugins {  
		     id 'java'  
			}  
			group 'org.example'  
			version '1.0-SNAPSHOT'  
			repositories {  
			  mavenCentral()  
			}  
			dependencies {  
			  implementation 'org.junit.jupiter:junit-jupiter:5.4.2'  
			  compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.22'  
			}  
			test {  
			  useJUnitPlatform()  
		> }
         1. **JUnit** v.5.4.2 pour effectuer les tests.
         2. **mysql-connector-java** v.8.0.22 pour communiquer avec la base de données.
         
   * Séparation des classes en **3 packages distincts**
   
      * **ConsoleInterface** : contient les classes nécessaires au bon fonctionnement de l'application (Chargement/Analyse/Affichage des données, Statut, Interruption…).
      * **GraphicInterface** : contient les classes nécessaires à la partie graphique de l'application.
      * **Exception** : contient les exceptions personnalisées.
      
   * Ajout des dossiers **factures** (contenant le fichier.csv), **src/icons** (contenant les icons.png) et **fichiersExternes/database** (contenant le code pour créer la base de données MySQL)

## Explication du fonctionnement de l'application

## Explication du GUI

### Acceuil

![alt acceuil](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/app.PNG?raw=true)

**Objectifs des boutons**

1. Démarrer le **chargement** des factures

2. Démarrer **l'analyse** des factures chargées

3. Afficher le **statut** de l'application 
	* ![alt statut](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/statut.PNG?raw=true)
	* **3 états possibles** : 
		1. Pret
		2. Chargement
		3. Analyse
		
4. **Afficher** la liste des fraudeurs suite à l'analyse
	* ![alt affichage](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/affichage.PNG?raw=true)
	
5. **Interrompre** l'application

7. **Supprimer** toutes les données (factures chargées ET fraudeurs trouvés)
	* ![alt confirmation](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/confirmation.PNG?raw=true)

8. **Quitter** l'application
