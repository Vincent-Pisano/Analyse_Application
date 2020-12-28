package ConsoleInterface;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ChargementAnalyseTest {

    //position des informations après un .split()
    final int POSITION_COMPAGNIE = 0;
    final int POSITION_DATE = 1;
    final int POSITION_MONTANT = 2;

    // factures complètes
    final String FACTURE_1 = "9903141,2019-12-28,3351.56";
    final String FACTURE_2 = "1546895,2019-12-28,3351.56";
    final String FACTURE_3_DIF_DATE_MONTANT = "9903141,2017-07-13,4568.85";

    // ** découpage de la string facture à l'avance pour éviter de surcharger les tests **
    // 1. On récupère le code de la compagnie
    final String FACTURE_COMPAGNIE_1 = FACTURE_1.split(",")[POSITION_COMPAGNIE];
    final String FACTURE_COMPAGNIE_2 = FACTURE_2.split(",")[POSITION_COMPAGNIE];
    final String FACTURE_COMPAGNIE_DIF_DATE_MONTANT = FACTURE_3_DIF_DATE_MONTANT.split(",")[POSITION_COMPAGNIE];

    // 2. On récupère la date de la facture
    final String FACTURE_DATE = FACTURE_1.split(",")[POSITION_DATE];
    final String FACTURE_DIF_DATE = FACTURE_3_DIF_DATE_MONTANT.split(",")[POSITION_DATE];

    // 2. On récupère le montant de la facture
    final Double FACTURE_MONTANT = Double.parseDouble(FACTURE_1.split(",")[POSITION_MONTANT]);
    final Double FACTURE_DIF_MONTANT = Double.parseDouble(FACTURE_3_DIF_DATE_MONTANT.split(",")[POSITION_MONTANT]);

    // Déclaration des classes nécessaires aux tests
    Map<FactureClee, List<String>> treeMapFactures = new TreeMap<>();
    WorkerChargement workerChargementTest = new WorkerChargement(treeMapFactures, "");
    WorkerAnalyse workerAnalyseTest = new WorkerAnalyse(treeMapFactures);

    // nécessaire pour communiquer avec la base de données, le FraudeCRUD de workerAnalyse est private
    FraudeCRUD fraudeCRUD = new FraudeCRUD();

    @Test
    void ajouterDeuxFacturesMemeDateMontant() {
        treeMapFactures.clear();

        //ajout dans la liste
        workerChargementTest.ajouterFacture(FACTURE_1);
        workerChargementTest.ajouterFacture(FACTURE_2);

        List<String> listeFacturesMemeDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DATE, FACTURE_MONTANT));

        assertEquals(FACTURE_COMPAGNIE_1, listeFacturesMemeDateMontant.get(0));
        assertEquals(FACTURE_COMPAGNIE_2, listeFacturesMemeDateMontant.get(1));

        treeMapFactures.clear();
    }

    @Test
    void ajouterDeuxFacturesDateMontantDifferents() {
        treeMapFactures.clear();

        //ajout dans la liste
        workerChargementTest.ajouterFacture(FACTURE_1);
        workerChargementTest.ajouterFacture(FACTURE_3_DIF_DATE_MONTANT);

        List<String> listeFacturesMemeDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DATE, FACTURE_MONTANT));
        List<String> listeFacturesDifferentesDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DIF_DATE, FACTURE_DIF_MONTANT));

        assertEquals(FACTURE_COMPAGNIE_1, listeFacturesMemeDateMontant.get(0));
        assertEquals(FACTURE_COMPAGNIE_DIF_DATE_MONTANT, listeFacturesDifferentesDateMontant.get(0));

        treeMapFactures.clear();
    }

    @Test
    void analyserDeuxFacturesMemeDateMontant() {
        treeMapFactures.clear();
        fraudeCRUD.supprimerAllFraudeurs();

        //ajout dans la liste
        workerChargementTest.ajouterFacture(FACTURE_1);
        workerChargementTest.ajouterFacture(FACTURE_2);

        List<String> listeFacturesMemeDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DATE, FACTURE_MONTANT));

        //analyse
        workerAnalyseTest.incrementRatio(listeFacturesMemeDateMontant);
        workerAnalyseTest.ajoutDansDatabase();

        //recherche dans la base de données
        assertEquals(new Fraudeur(FACTURE_COMPAGNIE_1, new Ratio(1, 1).calculPourcentageDuplique()),
                fraudeCRUD.readFraudeur(FACTURE_COMPAGNIE_1));
        assertEquals(new Fraudeur(FACTURE_COMPAGNIE_2, new Ratio(1, 1).calculPourcentageDuplique()),
                fraudeCRUD.readFraudeur(FACTURE_COMPAGNIE_2));

        treeMapFactures.clear();
        fraudeCRUD.supprimerAllFraudeurs();
    }

    @Test
    void analyserDeuxFacturesDateMontantDifferents() {
        treeMapFactures.clear();
        fraudeCRUD.supprimerAllFraudeurs();

        //ajout dans la liste
        workerChargementTest.ajouterFacture(FACTURE_1);
        workerChargementTest.ajouterFacture(FACTURE_3_DIF_DATE_MONTANT);

        List<String> listeFacturesMemeDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DATE, FACTURE_MONTANT));
        List<String> listeFacturesDifferentesDateMontant = treeMapFactures.get(new FactureClee(FACTURE_DIF_DATE, FACTURE_DIF_MONTANT));

        //analyse
        workerAnalyseTest.incrementRatio(listeFacturesMemeDateMontant);
        workerAnalyseTest.incrementRatio(listeFacturesDifferentesDateMontant);
        workerAnalyseTest.ajoutDansDatabase();

        //recherche dans la base de données
        assertNull(fraudeCRUD.readFraudeur(FACTURE_COMPAGNIE_1));
        assertNull(fraudeCRUD.readFraudeur(FACTURE_COMPAGNIE_2));

        treeMapFactures.clear();
        fraudeCRUD.supprimerAllFraudeurs();
    }
}