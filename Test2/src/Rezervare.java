public class Rezervare {
    private int id_rezervare;
    private int cod_rezervare;
    private int nr_locuri_rezervate;

    public Rezervare(int id_rezervare, int cod_rezervare, int nr_locuri_rezervate) {
        this.id_rezervare = id_rezervare;
        this.cod_rezervare = cod_rezervare;
        this.nr_locuri_rezervate = nr_locuri_rezervate;
    }

    public Rezervare() {
    }

    public void setId_rezervare(int id_rezervare) {
        this.id_rezervare = id_rezervare;
    }

    public void setCod_rezervare(int cod_rezervare) {
        this.cod_rezervare = cod_rezervare;
    }

    public void setNr_locuri_rezervate(int nr_locuri_rezervate) {
        this.nr_locuri_rezervate = nr_locuri_rezervate;
    }

    public int getId_rezervare() {
        return id_rezervare;
    }

    public int getCod_rezervare() {
        return cod_rezervare;
    }

    public int getNr_locuri_rezervate() {
        return nr_locuri_rezervate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rezervare{");
        sb.append("id_rezervare=").append(id_rezervare);
        sb.append(", cod_rezervare=").append(cod_rezervare);
        sb.append(", nr_locuri_rezervate=").append(nr_locuri_rezervate);
        sb.append('}');
        return sb.toString();
    }
}
