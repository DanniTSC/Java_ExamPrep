public class Tranzactie {
    private int cod;
    private String simbol;
    private String tip;
    private int cantitate;
    private float pret;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public Tranzactie(int cod, String simbol, String tip, int cantitate, float pret) {
        this.cod = cod;
        this.simbol = simbol;
        this.tip = tip;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("cod=").append(cod);
        sb.append(", simbol='").append(simbol).append('\'');
        sb.append(", tip='").append(tip).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pret=").append(pret);
        sb.append('}');
        return sb.toString();
    }
}
