package ConsoleInterface;

public class Ratio {

    private int nbrFacture;
    private int nbrFactureDuplique;

    Ratio()
    {
        nbrFacture = 0;
        nbrFactureDuplique = 0;
    }

    Ratio(int nbrFacture, int nbrFactureDuplique)
    {
        this.nbrFacture = nbrFacture;
        this.nbrFactureDuplique = nbrFactureDuplique;
    }

    public int getNbrFacture() {
        return nbrFacture;
    }

    public int getNbrFactureDuplique() {
        return nbrFactureDuplique;
    }

    public void incrementNbrFacture()
    {
        nbrFacture++;
    }

    public void incrementNbrFactureDuplique()
    {
        nbrFactureDuplique++;
    }

    public Double calculPourcentageDuplique()
    {
        return ((double)nbrFactureDuplique /(double)nbrFacture) * 100;
    }
}
