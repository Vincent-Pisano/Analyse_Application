package ConsoleInterface;

import java.util.*;

public class WorkerAnalyse extends Worker implements Runnable {

    private Map<String, Ratio> listAllCompagnies;
    private FraudeCRUD fraudeCrud;

    public WorkerAnalyse(Map<FactureClee, List<String>> syncTreeMapFactures) {
        super(syncTreeMapFactures);
        this.listAllCompagnies = new HashMap<>();
        this.fraudeCrud = new FraudeCRUD();
    }

    public int getNbrFraudeurs()
    {
        return fraudeCrud.readNbrFraudeurs();
    }

    public ArrayList<Fraudeur> getAllFraudeurs()
    {
        return fraudeCrud.readAllFraudeur();
    }

    public void supprimerAllFraudeurs()
    {
        fraudeCrud.supprimerAllFraudeurs();
    }

    @Override
    public void run() {
        if (running) {
            List<String> listCompagnieParDateMontant;

            for (Map.Entry<FactureClee, List<String>> entreeEnCours : syncTreeMapFactures.entrySet()) {
                listCompagnieParDateMontant = entreeEnCours.getValue();

                //on incrémente le ratio
                incrementRatio(listCompagnieParDateMontant);
                if (!running)
                    break;
            }
            ajoutDansDatabase();
            running = false;
        }
    }

    void incrementRatio(List<String> listCompagnieParDateMontant)
    {
        Ratio ratio;
        for(String compagnie : listCompagnieParDateMontant) {
            // on vérifie si la compagnie est déjà dans la liste
            if (listAllCompagnies.containsKey(compagnie)) {
                ratio = listAllCompagnies.get(compagnie);
            } else { //sinon, on crée un nouveau ratio
                ratio = new Ratio();
            }

            // on vérifie si il y a eu de la duplication
            if (listCompagnieParDateMontant.size() > 1) {
                ratio.incrementNbrFactureDuplique();
            }
            ratio.incrementNbrFacture();
            listAllCompagnies.put(compagnie, ratio);
        }
    }

    void ajoutDansDatabase()
    {
        for (Map.Entry<String, Ratio> fraudeurPotentiel : listAllCompagnies.entrySet()) {

            Double pourcentageDuplique = fraudeurPotentiel.getValue().calculPourcentageDuplique();
            //on vérifie si la compagnie est soupçonnée de fraude
            if (pourcentageDuplique >= 10.0)
            {
                fraudeCrud.ajouterFraudeur(fraudeurPotentiel.getKey(),
                                           fraudeurPotentiel.getValue().getNbrFacture(),
                                           fraudeurPotentiel.getValue().getNbrFactureDuplique(),
                                           pourcentageDuplique);
            }
        }
    }
}
