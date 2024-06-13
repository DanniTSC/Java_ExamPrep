public class Tranzactii {
    private  int cod;
    private  String simbol;
    private String tip;
    private int cantitate;
    private float pret;

    public int getCod() {
        return cod;
    }

    public String getSimbol() {
        return simbol;
    }

    public String getTip() {
        return tip;
    }

    public int getCantitate() {
        return cantitate;
    }

    public float getPret() {
        return pret;
    }

    public Tranzactii(int cod, String simbol, String tip, int cantitate, float pret) {
        this.cod = cod;
        this.simbol = simbol;
        this.tip = tip;
        this.cantitate = cantitate;
        this.pret = pret;
    }
}
