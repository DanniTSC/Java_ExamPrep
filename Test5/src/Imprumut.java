public class Imprumut {
    private String nume;
    private String cota;
    private int nrZile;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    public int getNrZile() {
        return nrZile;
    }

    public void setNrZile(int nrZile) {
        this.nrZile = nrZile;
    }

    public Imprumut(String nume, String cota, int nrZile) {
        this.nume = nume;
        this.cota = cota;
        this.nrZile = nrZile;
    }

    public Imprumut() {
    }
}
