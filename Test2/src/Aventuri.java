public class Aventuri {
    private int cod_aventura;
    private String denumire;
    private float tarif;
    private int locuri_disponibile;

    public Aventuri(int cod_aventura, String denumire, float tarif, int locuri_disponibile) {
        this.cod_aventura = cod_aventura;
        this.denumire = denumire;
        this.tarif = tarif;
        this.locuri_disponibile = locuri_disponibile;
    }

    public void setCod_aventura(int cod_aventura) {
        this.cod_aventura = cod_aventura;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public void setLocuri_disponibile(int locuri_disponibile) {
        this.locuri_disponibile = locuri_disponibile;
    }

    public int getCod_aventura() {
        return cod_aventura;
    }

    public String getDenumire() {
        return denumire;
    }

    public float getTarif() {
        return tarif;
    }

    public int getLocuri_disponibile() {
        return locuri_disponibile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Aventuri{");
        sb.append("cod_aventura=").append(cod_aventura);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tarif=").append(tarif);
        sb.append(", locuri_disponibile=").append(locuri_disponibile);
        sb.append('}');
        return sb.toString();
    }
}
