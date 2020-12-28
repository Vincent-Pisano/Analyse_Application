package ConsoleInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkerChargement extends Worker implements Runnable {

    final static int POSITION_COMPAGNIE = 0;
    final static int POSITION_DATE = 1;
    final static int POSITION_MONTANT = 2;

    private final String fichier;
    private BufferedReader reader;
    private int compteurFacture;

    public WorkerChargement(Map<FactureClee, List<String>> syncTreeMapFactures, String path) {
        super(syncTreeMapFactures);
        this.fichier = path;
    }

    public int getNbrFactures()
    {
        synchronized (syncTreeMapFactures)
        {
            return compteurFacture;
        }
    }

    public void reinitialiseNbrFactures()
    {
        synchronized (syncTreeMapFactures)
        {
            compteurFacture = 0;
        }
    }

    @Override
    void start() {
        super.start();
        compteurFacture = 0;
    }

    @Override
    public void run() {
        Path path = Paths.get("java",fichier);
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (running)
            lireFichier();
    }

    public void lireFichier()
    {
        try {
            String ligne;
            do {
                if (!running)
                    break;
                ligne = reader.readLine();
                if (ligne == null) {
                    running = false;
                } else {
                    ajouterFacture(ligne);
                }
            } while (ligne != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterFacture(String ligne)
    {
        List<String> listCompagnie;
        String[] listComposantFacture = ligne.split(",");
        FactureClee clee = new FactureClee(listComposantFacture[WorkerChargement.POSITION_DATE],
                Double.parseDouble(listComposantFacture[WorkerChargement.POSITION_MONTANT]));
        if (syncTreeMapFactures.containsKey(clee)) {
            listCompagnie = syncTreeMapFactures.get(clee);
        }
        else
        {
            listCompagnie = new LinkedList<>();
        }
        listCompagnie.add(listComposantFacture[POSITION_COMPAGNIE]);
        syncTreeMapFactures.put(clee, listCompagnie);

        synchronized (syncTreeMapFactures)
        {
            compteurFacture++;
        }
    }
}