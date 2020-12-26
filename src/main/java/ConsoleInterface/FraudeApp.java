package ConsoleInterface;
import java.util.*;

public class FraudeApp {

    Map<FactureClee, List<String>> syncTreeMapFactures = Collections.synchronizedSortedMap(new TreeMap<>());

    public static void main(String[] args) {

        FraudeApp app = new FraudeApp();
        WorkerChargement workerChargement = new WorkerChargement(app.syncTreeMapFactures, ".\\..\\factures\\factures.csv");
        WorkerAnalyse workerAnalyse = new WorkerAnalyse(app.syncTreeMapFactures);

        char requete;
        Scanner scan = new Scanner(System.in);


        System.out.println("================ \n" +
                "c: charger le fichier facture.csv \n" +
                "a: analyser les factures \n" +
                "f: afficher les fraudeurs \n" +
                "s: afficher le statut \n" +
                "i: interrompre\n" +
                "q: terminer et quitter\n");

        do {
            System.out.print("Entrez votre choix (c, a, f, s, i, ou q) : ");
            requete = scan.next().toLowerCase().charAt(0);

            switch (requete)
            {
                case 'c':
                    // charger le fichier facture
                    if (workerChargement.getEtat())
                    {
                        System.out.println("Patientez pendant la fin du chargement");
                    }
                    else if (workerAnalyse.getEtat())
                    {
                        System.out.println("Patientez pendant la fin de l'analyse");
                    }
                    else {
                        // on veut éviter de supprimer manuellement les fraudeurs dans
                        // la base de données à chaque lancement
                        if (workerAnalyse.getNbrFraudeurs() != 0)
                        {
                            workerAnalyse.supprimerAllFraudeurs();
                        }
                        workerChargement.start();
                    }
                    break;

                case 'a':
                    // analyse des factures
                    if (workerChargement.getEtat()) {
                        System.out.println("Patientez pendant la fin du chargement");
                    }
                    else if (workerAnalyse.getEtat())
                    {
                        System.out.println("Patientez pendant la fin de l'analyse");
                    }
                    else {
                            workerAnalyse.start();
                    }
                    break;

                case 'f':
                    //affichage des fraudeurs
                    if (!workerAnalyse.getEtat())
                    {
                        ArrayList<Fraudeur> fraudeurs = workerAnalyse.getAllFraudeurs();
                        for (Fraudeur fraudeur : fraudeurs)
                        {
                            System.out.println(fraudeur.getCompagnie() + " " + Math.round(fraudeur.getRatio())
                                    + "% de factures dupliquees");
                        }
                    }
                    else {
                        System.out.println("Patientez pendant la fin de l'analyse");
                    }
                    break;

                case 's':
                    // affichage du statut
                    System.out.println("--------------------------------");
                    System.out.println("Status");
                    System.out.println("--------------------------------");
                    System.out.print("Etat : ");
                    if (workerChargement.getEtat())
                        System.out.println("Chargement");
                    else if (workerAnalyse.getEtat())
                        System.out.println("Analyse");
                    else
                        System.out.println("Prêt");
                    System.out.println("Factures chargees : " + workerChargement.getNbrFactures());
                    System.out.println("Fraudeurs trouvés : " + workerAnalyse.getNbrFraudeurs());
                    break;

                case 'i':
                    // interruption du thread
                    if (workerChargement.getEtat()) {
                        workerChargement.interrompre();
                        System.out.println("Interruption du chargement");
                    }
                    else if (workerAnalyse.getEtat()) {
                        workerAnalyse.interrompre();
                        System.out.println("Interruption de l'analyse");
                    }
                    else
                        System.out.println("Aucune opération à interrompre");
                    break;

                case 'q':
                    // on quitte de l'application
                    if(workerChargement.getEtat() || workerAnalyse.getEtat()) {
                        System.out.println("Patientez pendant l'arrêt des opérations");
                        workerChargement.attendre();
                        workerAnalyse.attendre();
                    }
                        System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide : " + requete);
                    break;
            }
        } while (requete != 'q');
    }

}
