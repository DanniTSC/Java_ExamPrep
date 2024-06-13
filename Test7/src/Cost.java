public class Cost {
    private int cod_cost;
    private int cod_proiect;
    private String denumire_cost;
    private String unitateMasura;
    private float cantitate;
    private float pretUnitar;

    public int getCod_cost() {
        return cod_cost;
    }

    public void setCod_cost(int cod_cost) {
        this.cod_cost = cod_cost;
    }

    public int getCod_proiect() {
        return cod_proiect;
    }

    public void setCod_proiect(int cod_proiect) {
        this.cod_proiect = cod_proiect;
    }

    public String getDenumire_cost() {
        return denumire_cost;
    }

    public void setDenumire_cost(String denumire_cost) {
        this.denumire_cost = denumire_cost;
    }

    public String getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(String unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public float getCantitate() {
        return cantitate;
    }

    public void setCantitate(float cantitate) {
        this.cantitate = cantitate;
    }

    public float getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(float pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public Cost(int cod_cost, int cod_proiect, String denumire_cost, String unitateMasura, float cantitate, float pretUnitar) {
        this.cod_cost = cod_cost;
        this.cod_proiect = cod_proiect;
        this.denumire_cost = denumire_cost;
        this.unitateMasura = unitateMasura;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
    }
}
