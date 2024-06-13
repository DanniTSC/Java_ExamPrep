import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TranzactiiReader {
    public List<Tranzactii> citesteTranzactii(String path)
    {
        List<Tranzactii> tranzactii = new ArrayList<>();
        try {
            File fisier = new File(path);
            Scanner scanner = new Scanner(fisier);
            while(scanner.hasNextLine())
            {
                String linie = scanner.nextLine();
                String[] elem = linie.split(",");
                int cod = Integer.parseInt(elem[0].trim());
                String simbol = elem[1].trim();
                String tip = elem[2].trim();
                int cantitate = Integer.parseInt(elem[3].trim());
                float pret = Float.parseFloat(elem[4].trim());
                Tranzactii tranzactii1 = new Tranzactii(cod,simbol,tip,cantitate,pret);
                tranzactii.add(tranzactii1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tranzactii;
    }

}
