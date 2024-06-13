public class Programare {
    private int cod;
    private    String zi;
    private String ora;
    private String sala;
    private String facultate;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public Programare(int cod, String zi, String ora, String sala, String facultate) {
        this.cod = cod;
        this.zi = zi;
        this.ora = ora;
        this.sala = sala;
        this.facultate = facultate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Programare{");
        sb.append("cod=").append(cod);
        sb.append(", zi='").append(zi).append('\'');
        sb.append(", ora='").append(ora).append('\'');
        sb.append(", sala='").append(sala).append('\'');
        sb.append(", facultate='").append(facultate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
