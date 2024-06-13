import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Tranzactie> tranzactieList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/bursa_tranzactii.txt"))){
            String linie;
            while((linie = bf.readLine()) != null) {
                String[] elem = linie.split(",");
                Tranzactie tranzactie = new Tranzactie(
                        Integer.parseInt(elem[0]), elem[1], elem[2], Integer.parseInt(elem[3]), Float.parseFloat(elem[4])
                );
                tranzactieList.add(tranzactie);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        List<Persona> personaList = new ArrayList<>();
        try(var con = DriverManager.getConnection("jdbc:sqlite:src/bursa.db");
        var cmd = con.createStatement();
        var r = cmd.executeQuery("SELECT * FROM PERSOANE")){
            while (r.next())
            {
                Persona p = new Persona(r.getString(2),r.getInt(1),r.getString(3)
                );
                personaList.add(p);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        personaList.forEach(persona -> System.out.println("CNP: " + persona.getCNP()));

        long nrRez8 = personaList.stream()
                .filter(persona -> String.valueOf(persona.getCNP()).startsWith("8"))
                .count();

        long nrRez9 = personaList.stream()
                .filter(persona -> String.valueOf(persona.getCNP()).startsWith("9"))
                .count();

        long rezTot = nrRez8 + nrRez9;
        System.out.println("NR REZIDENTI :" + rezTot);
        //cer2 BRD -> 14 tranzactii
        Map<String, Long> tranzactiiPerSimbol = tranzactieList.stream()
                .collect(Collectors.groupingBy(Tranzactie::getSimbol,Collectors.counting()));
        tranzactiiPerSimbol.forEach(((s, aLong) -> {
            System.out.println(s + "->" + aLong+ " Tranzzactii");
        }));
        //in simboluri.txt lista pentru care exista tranzactii, doar o data sa apara
        List<String> simbol =
                tranzactieList.stream().map(Tranzactie::getSimbol).distinct().
                        collect(Collectors.toList());
       // tranzactieList.stream().map(Tranzactie::getSimbol).findFirst().orElse(null);
        try(PrintWriter pw = new PrintWriter(new File("src/simboluri.txt"))){
        simbol.forEach(tranzactie -> pw.println(tranzactie + "\n"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //pt fiecare client sa se afiseze portofoliul cu nume apoi acituni pentru fiecare simbol,
        //nr de actiuni actiuni cumparate minus cele vandute
        Map<String, Map<String, Integer>> portofoliu = new HashMap<>();
        for(Tranzactie tranzactie : tranzactieList)
        {
            Persona client = personaList.stream().
                    filter(p -> p.getCod() == tranzactie.getCod()).findFirst().orElse(null);
            if(client!=null)
            {
                String numeClient = client.getNume();
                String simboll = tranzactie.getSimbol();
                int cantitate = tranzactie.getCantitate();

                portofoliu.putIfAbsent(numeClient,new HashMap<>());
                Map<String , Integer> actiuniclient = portofoliu.get(numeClient);

                if(tranzactie.getTip().equalsIgnoreCase("cumparare"))
                {
                    actiuniclient.merge(simboll,cantitate,Integer::sum);
                }else if(tranzactie.getTip().equalsIgnoreCase("vanzare"))
                {
                    actiuniclient.merge(simboll,-cantitate,Integer::sum);
                }
            }

        }
        portofoliu.entrySet().forEach(System.out::println);

        Thread server = new Thread(new Server(8080,personaList,tranzactieList));
        server.start();
        Thread client = new Thread(new Client(8080,"localhost",1));
        client.start();
        try{
            server.join();
            client.join();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }//eom
}//eoc