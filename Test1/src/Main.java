import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String tranzactiiPath = "src/bursa_tranzactii.txt";
        String dbPath = "src/bursa.db";

        PersoaneReader persoaneReader = new PersoaneReader();
        List<Persoane> persoaneList = persoaneReader.citestePersoane(dbPath);

        TranzactiiReader tranzactiiReader = new TranzactiiReader();
        List<Tranzactii> tranzactiiList = tranzactiiReader.citesteTranzactii(tranzactiiPath);

        long numarNerezidenti = persoaneList.stream().filter(p-> p .getCnp().startsWith("8")|| p.getCnp().startsWith("9")).count();
        System.out.println("Nr Nerezidenti:"+numarNerezidenti);

        //cer2
//        Map<String,Integer> tranzactiiPerSimbol = new HashMap<>();
//       for(Tranzactii tranzactie : tranzactiiList)
//        {
//            tranzactiiPerSimbol.put(tranzactie.getSimbol(), tranzactiiPerSimbol.getOrDefault(tranzactie.getSimbol(),0)+1);
//
//        }
//        System.out.println("Nr tranzactii:");
//        for(Map.Entry<String, Integer> entry : tranzactiiPerSimbol.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue() + " trazactii");
//        }
            //cer2
            Map<String,Integer> tranzactiiPerSimbol = new HashMap<>();
            for(Tranzactii tranzactie : tranzactiiList) {
                String simbol = tranzactie.getSimbol();
                int count = tranzactiiPerSimbol.getOrDefault(simbol,0);
                tranzactiiPerSimbol.put(simbol,count+1);
            }
        for(Map.Entry<String, Integer> entry : tranzactiiPerSimbol.entrySet()) {
           System.out.println(entry.getKey() + " " + entry.getValue() + " trazactii");
       }
//cer 3
        try {
            PrintWriter pw = new PrintWriter(new File("src/simboluri.txt"));
            for (String simbol : tranzactiiPerSimbol.keySet()) {
                pw.write(simbol + "\n");
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Integer, Map<String, Integer>> protofolii = new HashMap<>();
        for(Tranzactii tranzactie : tranzactiiList)
        {
            int codClient = tranzactie.getCod();
            String simbol = tranzactie.getSimbol();
            int cantitate = tranzactie.getCantitate();
            String tip = tranzactie.getTip();

            protofolii.putIfAbsent(codClient, new HashMap<>());
            Map<String, Integer> portofoliu = protofolii.get(codClient);

            int cantitateCurenta = portofoliu.getOrDefault(simbol, 0);
            if(tip.equalsIgnoreCase("cumparare"))
            {
                cantitateCurenta += cantitate;
            } else if (tip.equalsIgnoreCase("vanzare")) {
                cantitateCurenta-=cantitate;
            }
            portofoliu.put(simbol,cantitateCurenta);
        }

        //cer 4
        System.out.println("PORTOFOLII:");
        for(Map.Entry<Integer, Map<String,Integer>> entry : protofolii.entrySet())
                {
                    int codCleint = entry.getKey();
                    Map<String, Integer> portofoliu = entry.getValue();
                    Persoane client = persoaneList.stream().
                            filter(p->p.getCod() == codCleint).findFirst().orElse(null);
                    if(client!=null)
                    {
                        System.out.println(client.getNume()+":");
                        for(Map.Entry<String,Integer> portofoliuEntry : portofoliu.entrySet())
                        {
                            System.out.println(portofoliuEntry.getKey() + " " + portofoliuEntry.getValue());
                        }
                    }
                }

    }//end of main method
}//end of main class