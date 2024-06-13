public class Disciplina {
    private int codDisciplina;
    private String nume ;
    private String optionalitate;

    public Disciplina(int codDisciplina, String nume, String optionalitate) {
        this.codDisciplina = codDisciplina;
        this.nume = nume;
        this.optionalitate = optionalitate;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getOptionalitate() {
        return optionalitate;
    }

    public void setOptionalitate(String optionalitate) {
        this.optionalitate = optionalitate;
    }

    public Disciplina() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Disciplina{");
        sb.append("codDisciplina=").append(codDisciplina);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", optionalitate='").append(optionalitate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
