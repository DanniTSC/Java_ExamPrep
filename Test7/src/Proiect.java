public class Proiect {
    private int cod_proiect;
    private String nume;
    private String descriere;
    private  String manager;
            private double buget;

    public int getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(int cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public double getBuget() {
        return buget;
    }

    public void setBuget(double buget) {
        this.buget = buget;
    }

    public Proiect(int cod_proiect, String nume, String descriere, String manager, double buget) {
        this.cod_proiect = cod_proiect;
        this.nume = nume;
        this.descriere = descriere;
        this.manager = manager;
        this.buget = buget;
    }
}
