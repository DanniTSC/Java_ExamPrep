public class Produs {
    private int cod_produs;
    private String nume;
    private double pret;

    public int getCod_produs() {
        return cod_produs;
    }

    public void setCod_produs(int cod_produs) {
        this.cod_produs = cod_produs;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public Produs() {
    }

    public Produs(int cod_produs, String nume, double pret) {
        this.cod_produs = cod_produs;
        this.nume = nume;
        this.pret = pret;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("cod_produs=").append(cod_produs);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", pret=").append(pret);
        sb.append('}');
        return sb.toString();
    }
}
