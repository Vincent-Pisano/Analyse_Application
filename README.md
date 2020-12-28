# Bienvenue sur Analyse_Application

Ce projet est une **application java graphique** (Swing) permettant le **chargement** de données récupérées depuis un fichier.csv dans une collection synchronisée . Les données sont ensuite **analysées**. Les opérations sont effectuées en **arrière plan** pour ne pas bloquer l'utilisateur lors de ses requêtes. Les compagnies frauduleuses sont finalement affichées une fois que l'analyse est terminée et que le bouton d'affichage est appuyé.

On estime qu'une compagnie est frauduleuse si **plus de 10%** de ses factures ont le même montant et la même date qu'une facture d'une autre compagnie

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
      
   * Ajout des dossiers **factures** (contenant le fichier.csv), **src/icons** (contenant les icons.png) et **fichiersExterne/database** (contenant le code pour créer la base de données MySQL).

## Explication du fonctionnement de l'application

Les différentes options sont organisées dans une classe centrale : FraudeApp, qui contient le TreeMap synchronisé et les deux enfants des la classe Worker (permettant de travailler sur d'autre threads) :
1. WorkerChargement
	* Utilisation de NIO pour lire le fichier.csv
	* Rangement des informations dans le TreeMap synchronisé avec la clé composé FactureClee (date et montant de la facture)
2. WorkerAnalyse
    * Analyse de toutes les factures dans le TreeMap synchronisé et rangement dans un HashMap local depuis le numéro de compagnie toutes les compagnies avec un ratio (nbrFactureDuplique/nbrFacture) supérieur à 10.
    * Écriture dans la table fraudeur grâce à la classe FraudeCRUD qui assure la connexion à la base de données MySQL des compagnies frauduleuses, de leur nombre de factures totales et dupliquées ainsi que du ratio.
 
La classe FraudeApp contient les méthodes suivantes :
 * lancementChargement()
	 * lance le thread du WorkerChargement
 * lancementAnalyse()
	 * lance le thread du WorkerAnalyse
 * afficherFraudeurs()
	 * Envoie un ArrayList de Fraudeur
 * afficherStatut()
	 * Envoie une instance de la classe Statut avec les informations actuelles
 * interrompre()
	 * Interrompt les threads si ils sont en train de tourner en arrière plan
 * supprimerTout()
	 * Supprime le contenue du TreeMap synchronisé, du compteur de facture de WorkerChargement ainsi que de la table fraudeurs de la base de données.
 * quitter()
	 * Ferme l'application

### Utilisation de Junit v5.4.2 pour tester les méthodes

La classe de test ChargementAnalyseTest se charge des tests suivants :
   * ajouterDeuxFacturesMemeDateMontant()
   * ajouterDeuxFacturesDateMontantDifferents()
   * analyserDeuxFacturesMemeDateMontant()
   * analyserDeuxFacturesDateMontantDifferents()
   
La classe de test FraudeCRUDTest se charge des tests suivants :
   * ajouterEtRechercherFraudeur()
   * supprimerFraudeur()
   * readAllFraudeur()
   * supprimerAllFraudeur()
   

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

6. **Supprimer** toutes les données (factures chargées ET fraudeurs trouvés)
	* ![alt confirmation](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/confirmation.PNG?raw=true)

7. **Quitter** l'application

### Gestion des erreurs:

![alt erreur](https://github.com/Vincent-Pisano/Analyse_Application/blob/master/fichiersExternes/imagesGUI/erreur.PNG?raw=true)

Un **titre** : Erreur

Un **message** : dépend de l'erreur invoquée :
  *  Patientez pendant la fin de l'analyse
  * Patientez pendant la fin du chargement
  * Aucun fraudeur dans la base de données
   * Aucune opération en cours

Un **bouton** Ok
