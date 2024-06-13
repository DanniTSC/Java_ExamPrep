public class Capitol {
    private int cod_capitol;
    private int cod_santier;
    private String denumire;
    private String unitateMasura;
    private double cantitate;
    private double pretUnitar;

    public Capitol(int cod_capitol, int cod_santier, String denumire, String unitateMasura, double cantitate, double pretUnitar) {
        this.cod_capitol = cod_capitol;
        this.cod_santier = cod_santier;
        this.denumire = denumire;
        this.unitateMasura = unitateMasura;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
    }

    public int getCod_capitol() {
        return cod_capitol;
    }

    public void setCod_capitol(int cod_capitol) {
        this.cod_capitol = cod_capitol;
    }

    public int getCod_santier() {
        return cod_santier;
    }

    public void setCod_santier(int cod_santier) {
        this.cod_santier = cod_santier;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getUnitateMasura() {
        return unitateMasura;
    }

    public void setUnitateMasura(String unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public double getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(double pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Capitol{");
        sb.append("cod_capitol=").append(cod_capitol);
        sb.append(", cod_santier=").append(cod_santier);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", unitateMasura='").append(unitateMasura).append('\'');
        sb.append(", cantitate=").append(cantitate);
        sb.append(", pretUnitar=").append(pretUnitar);
        sb.append('}');
        return sb.toString();
    }
}


