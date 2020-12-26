package ConsoleInterface;

import java.util.Objects;

public class Fraudeur {

    private String compagnie;
    private Double ratio;

    public Fraudeur(String compagnie, Double ratio) {
        this.compagnie = compagnie;
        this.ratio = ratio;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraudeur fraudeur = (Fraudeur) o;
        return Objects.equals(compagnie, fraudeur.compagnie) &&
                Objects.equals(ratio, fraudeur.ratio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compagnie, ratio);
    }
}
