public class Tranzactie {
    private int codProdus;
    private int cantitate;
    private TipTranzactie tipTranzactie;

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public TipTranzactie getTipTranzactie() {
        return tipTranzactie;
    }

    public void setTipTranzactie(TipTranzactie tipTranzactie) {
        this.tipTranzactie = tipTranzactie;
    }

    public Tranzactie(int codProdus, int cantitate, TipTranzactie tipTranzactie) {
        this.codProdus = codProdus;
        this.cantitate = cantitate;
        this.tipTranzactie = tipTranzactie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tranzactie{");
        sb.append("codProdus=").append(codProdus);
        sb.append(", cantitate=").append(cantitate);
        sb.append(", tipTranzactie=").append(tipTranzactie);
        sb.append('}');
        return sb.toString();
    }
}
