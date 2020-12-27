package ConsoleInterface;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FraudeCRUDTest {
    final String COMPAGNIE = "1234567";
    final int NBR_FACTURE = 500;
    final int NBR_FACTURE_DUPLIQUE = 250;

    final String COMPAGNIE_2 = "9876543";
    final int NBR_FACTURE_2 = 300;
    final int NBR_FACTURE_DUPLIQUE_2 = 100;

    final Ratio RATIO = new Ratio(NBR_FACTURE, NBR_FACTURE_DUPLIQUE);
    final Ratio RATIO_2 = new Ratio(NBR_FACTURE_2, NBR_FACTURE_DUPLIQUE_2);

    FraudeCRUD fraudeurCRUD = new FraudeCRUD();

    @Test
    void ajouterEtRechercherFraudeur() {
        Fraudeur fraudeur;
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);

        fraudeurCRUD.ajouterFraudeur(COMPAGNIE, NBR_FACTURE, NBR_FACTURE_DUPLIQUE, RATIO.calculPourcentageDuplique());
        fraudeur = fraudeurCRUD.readFraudeur(COMPAGNIE);

        assertEquals(new Fraudeur(COMPAGNIE, RATIO.calculPourcentageDuplique()), fraudeur);

        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);
    }

    @Test
    void supprimerFraudeur() {
        Fraudeur fraudeur;
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);

        fraudeurCRUD.ajouterFraudeur(COMPAGNIE, NBR_FACTURE, NBR_FACTURE_DUPLIQUE, RATIO.calculPourcentageDuplique());
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);

        fraudeur = fraudeurCRUD.readFraudeur(COMPAGNIE);
        assertNull(fraudeur);

        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);
    }

    @Test
    void readAllFraudeur() {
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE_2);

        fraudeurCRUD.ajouterFraudeur(COMPAGNIE, NBR_FACTURE, NBR_FACTURE_DUPLIQUE, RATIO.calculPourcentageDuplique());
        fraudeurCRUD.ajouterFraudeur(COMPAGNIE_2, NBR_FACTURE_2, NBR_FACTURE_DUPLIQUE_2, RATIO_2.calculPourcentageDuplique());

        ArrayList<Fraudeur> fraudeurs = fraudeurCRUD.readAllFraudeur();

        assertEquals(new Fraudeur(COMPAGNIE, RATIO.calculPourcentageDuplique()), fraudeurs.get(0));
        assertEquals(new Fraudeur(COMPAGNIE_2, RATIO_2.calculPourcentageDuplique()), fraudeurs.get(1));

        fraudeurCRUD.supprimerFraudeur(COMPAGNIE);
        fraudeurCRUD.supprimerFraudeur(COMPAGNIE_2);
    }

    @Test
    void supprimerAllFraudeur() {
        fraudeurCRUD.supprimerAllFraudeurs();

        fraudeurCRUD.ajouterFraudeur(COMPAGNIE, NBR_FACTURE, NBR_FACTURE_DUPLIQUE, RATIO.calculPourcentageDuplique());
        fraudeurCRUD.ajouterFraudeur(COMPAGNIE_2, NBR_FACTURE_2, NBR_FACTURE_DUPLIQUE_2, RATIO_2.calculPourcentageDuplique());

        fraudeurCRUD.supprimerAllFraudeurs();

        ArrayList<Fraudeur> fraudeurs = fraudeurCRUD.readAllFraudeur();
        assertNull(fraudeurs);

        fraudeurCRUD.supprimerAllFraudeurs();
    }


}