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


