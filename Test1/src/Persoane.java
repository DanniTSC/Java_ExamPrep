public class Persoane {
    private int cod;
    private String cnp;
    private  String nume;

    public Persoane(int cod, String cnp, String nume) {
        this.cod = cod;
        this.cnp = cnp;
        this.nume = nume;
    }

    public int getCod() {
        return cod;
    }

    public String getCnp() {
        return cnp;
    }

    public String getNume() {
        return nume;
    }
}

