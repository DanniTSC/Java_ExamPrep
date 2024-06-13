public class Carte {
    private String cota;
    private String titlu;
    private String autor;
    private int anLansare;

    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnLansare() {
        return anLansare;
    }

    public void setAnLansare(int anLansare) {
        this.anLansare = anLansare;
    }

    public Carte() {
    }

    public Carte(String cota, String titlu, String autor, int anLansare) {
        this.cota = cota;
        this.titlu = titlu;
        this.autor = autor;
        this.anLansare = anLansare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("cota='").append(cota).append('\'');
        sb.append(", titlu='").append(titlu).append('\'');
        sb.append(", autor='").append(autor).append('\'');
        sb.append(", anLansare=").append(anLansare);
        sb.append('}');
        return sb.toString();
    }
}
