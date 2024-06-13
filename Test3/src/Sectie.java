public class Sectie {
    private int cod_sectie;
    private String denumire;
    private int numar_locuri;

    public int getCod_sectie() {
        return cod_sectie;
    }

    public void setCod_sectie(int cod_sectie) {
        this.cod_sectie = cod_sectie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumar_locuri() {
        return numar_locuri;
    }

    public void setNumar_locuri(int numar_locuri) {
        this.numar_locuri = numar_locuri;
    }

    public Sectie(int cod_sectie, String denumire, int numar_locuri) {
        this.cod_sectie = cod_sectie;
        this.denumire = denumire;
        this.numar_locuri = numar_locuri;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sectie{");
        sb.append("cod_sectie=").append(cod_sectie);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", numar_locuri=").append(numar_locuri);
        sb.append('}');
        return sb.toString();
    }
}
