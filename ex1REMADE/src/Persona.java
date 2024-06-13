public class Persona {
    private String CNP;
    private int cod;
    private String nume;

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Persona{");
        sb.append("CNP='").append(CNP).append('\'');
        sb.append(", cod=").append(cod);
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Persona(String CNP, int cod, String nume) {
        this.CNP = CNP;
        this.cod = cod;
        this.nume = nume;
    }
}
