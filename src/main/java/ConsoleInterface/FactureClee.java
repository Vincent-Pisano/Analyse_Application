package ConsoleInterface;

public class FactureClee implements Comparable{
    private String dateFacture;
    private Double montantFacture;

    public FactureClee(String dateFacture, Double montantFacture) {
        this.dateFacture = dateFacture;
        this.montantFacture = montantFacture;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(Double montantFacture) {
        this.montantFacture = montantFacture;
    }

    @Override
    public int compareTo(Object o)
    {
        FactureClee clee = (FactureClee)o;
        if (montantFacture.equals(clee.getMontantFacture()))
        {
            return -1 * dateFacture.compareTo(clee.getDateFacture());
        }
        return -1 * montantFacture.compareTo(clee.getMontantFacture());
    }
}
