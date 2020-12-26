package ConsoleInterface;

public class Statut {

    private String etat;
    private int nbrFacturesChargees;
    private int nbrFraudeursTrouves;

    public Statut(String etat, int nbrFacturesChargees, int nbrFraudeursTrouves) {
        this.etat = etat;
        this.nbrFacturesChargees = nbrFacturesChargees;
        this.nbrFraudeursTrouves = nbrFraudeursTrouves;
    }

    public Statut() {
        this.etat = "";
        this.nbrFacturesChargees = 0;
        this.nbrFraudeursTrouves = 0;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNbrFacturesChargees() {
        return nbrFacturesChargees;
    }

    public void setNbrFacturesChargees(int nbrFacturesChargees) {
        this.nbrFacturesChargees = nbrFacturesChargees;
    }

    public int getNbrFraudeursTrouves() {
        return nbrFraudeursTrouves;
    }

    public void setNbrFraudeursTrouves(int nbrFraudeursTrouves) {
        this.nbrFraudeursTrouves = nbrFraudeursTrouves;
    }
}
