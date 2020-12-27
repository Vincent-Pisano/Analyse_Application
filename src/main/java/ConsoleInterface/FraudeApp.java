package ConsoleInterface;
import java.util.*;
import Exception.*;

public class FraudeApp {

    private Map<FactureClee, List<String>> syncTreeMapFactures;
    private WorkerChargement workerChargement;
    private WorkerAnalyse workerAnalyse;

    public FraudeApp(String path) {
        syncTreeMapFactures = Collections.synchronizedSortedMap(new TreeMap<>());
        workerChargement = new WorkerChargement(syncTreeMapFactures, path);
        workerAnalyse = new WorkerAnalyse(syncTreeMapFactures);
    }

    public void lancementChargement() throws ExceptionChargementIsRunning, ExceptionAnalyseIsRunning
    {
        if (workerChargement.getEtat())
        {
            throw new ExceptionChargementIsRunning();
        }
        else if (workerAnalyse.getEtat())
        {
            throw new ExceptionAnalyseIsRunning();
        }
        // on veut éviter de supprimer manuellement les fraudeurs dans
        // la base de données à chaque lancement
        if (workerAnalyse.getNbrFraudeurs() != 0)
        {
            workerAnalyse.supprimerAllFraudeurs();
        }
        workerChargement.start();
    }

    public void lancementAnalyse() throws ExceptionChargementIsRunning, ExceptionAnalyseIsRunning
    {
        if (workerChargement.getEtat())
        {
            throw new ExceptionChargementIsRunning();
        }
        else if (workerAnalyse.getEtat())
        {
            throw new ExceptionAnalyseIsRunning();
        }
        workerAnalyse.start();
    }

    public ArrayList<Fraudeur> afficherFraudeurs() throws ExceptionAnalyseIsRunning, ExceptionListeFraudeursIsNull {
        if (workerAnalyse.getEtat())
        {
            throw new ExceptionAnalyseIsRunning();
        }
        ArrayList<Fraudeur> fraudeurs = workerAnalyse.getAllFraudeurs();
        if (fraudeurs != null)
        {
            return fraudeurs;
        }
        else {
            throw new ExceptionListeFraudeursIsNull();
        }
    }

    public Statut afficherStatut()
    {
        Statut statut = new Statut();
        if (workerChargement.getEtat())
            statut.setEtat("Chargement");
        else if (workerAnalyse.getEtat())
            statut.setEtat("Analyse");
        else
            statut.setEtat("Pret");
        statut.setNbrFacturesChargees(workerChargement.getNbrFactures());
        statut.setNbrFraudeursTrouves(workerAnalyse.getNbrFraudeurs());
        return statut;
    }

    public void interrompre() throws ExceptionNoOperationAInterrompre
    {
        if (workerChargement.getEtat()) {
            workerChargement.interrompre();
        }
        else if (workerAnalyse.getEtat()) {
            workerAnalyse.interrompre();
        }
        else
            throw new ExceptionNoOperationAInterrompre();
    }

    public void supprimerTout()
    {
        workerAnalyse.supprimerAllFraudeurs();
        syncTreeMapFactures = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    public void quitter() throws ExceptionChargementIsRunning, ExceptionAnalyseIsRunning {
        if (workerChargement.getEtat())
        {
            workerChargement.attendre();
            throw new ExceptionChargementIsRunning();
        }
        else if (workerAnalyse.getEtat())
        {
            workerAnalyse.attendre();
            throw new ExceptionAnalyseIsRunning();
        }
    }
}
