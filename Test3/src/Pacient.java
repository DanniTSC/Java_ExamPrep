public class Pacient {
    private long cnp;
    private String nume;
    private int varsta;
    private int cod_sectie;


    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public Pacient() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public  int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCod_sectie() {
        return cod_sectie;
    }

    public void setCod_sectie(int cod_sectie) {
        this.cod_sectie = cod_sectie;
    }

    public Pacient(long cnp, String nume, int varsta, int cod_sectie) {
        this.cnp = cnp;
        this.nume = nume;
        this.varsta = varsta;
        this.cod_sectie = cod_sectie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pacient{");
        sb.append("cnp=").append(cnp);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", varsta=").append(varsta);
        sb.append(", cod_sectie=").append(cod_sectie);
        sb.append('}');
        return sb.toString();
    }


}

