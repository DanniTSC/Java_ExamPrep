import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        List<Santier> santierList = new ArrayList<>();
        try(var fisier = new FileReader("src/santiere.json"))
        {
            var jsonArray = new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < jsonArray.length(); i++) {
                var jsonObj = jsonArray.getJSONObject(i);
                Santier s = new Santier(
                  jsonObj.getInt("Cod Santier"),
                  jsonObj.getString("Localitate"),
                  jsonObj.getString("Strada"),
                        jsonObj.getString("Obiectiv"),
                        jsonObj.getDouble("Valoare")
                );
                santierList.add(s);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        List<Capitol> capitolList = new ArrayList<>();
        try(var con = DriverManager.getConnection("jdbc:sqlite:src/devize.db");
        var cmd = con.createStatement();
        var res = cmd.executeQuery("SELECT * From CAPITOLE"))
        {
            while(res.next())
            {
                Capitol c = new Capitol(
                  res.getInt(1),
                  res.getInt(2),
                  res.getString(3),
                  res.getString(4),
                  res.getDouble(5),
                  res.getDouble(6)
                );
                capitolList.add(c);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    //cer1
    OptionalDouble medie = santierList.stream().mapToDouble(Santier::getValoare).average();
      santierList.stream().forEach(System.out::println);
        System.out.println("Medie:" + medie);
//    Double medieTest = santierList.stream().mapToDouble(Santier::getValoare).average().getAsDouble();
//        System.out.println(medieTest);
        //cer2
        Map<Integer, Double> mapa = new HashMap<>();
        for(Capitol capitol:capitolList)
        {
            int codCap = capitol.getCod_capitol();
            double cantitate = capitol.getCantitate();
            mapa.merge(codCap,cantitate,Double::sum); // ca aia cu exist daca exista il pun peste ca suma
        }

        mapa.forEach((cod, cantitate) -> {
            System.out.println("Cod: " + cod + ", Cantitate:" + cantitate);
        });
        //mapa.merge(capitolList.stream().mapToInt(Capitol::getCod_capitol),
        // capitolList.stream().mapToDouble(Capitol::getCantitate),
        // Double::sum);
        //e stream si nu merge maparea mea decat sa i dau collect sau get as

        //cer33 cod capitol dupa cod santier si val, valoare e cantitate ori pret
        Map<Integer,Map<Integer,Double>> ex3 = new HashMap<>();
//        capitolList.stream().filter(capitol -> {
//            santierList.stream().filter(santier -> santier.getCodSantier() == capitol.getCod_capitol());
//          return capitol.getCod_capitol();
//        });


        //ex3.values().add() = capitolList.stream().forEach(capitol -> capitol.getCantitate() * capitol.getPretUnitar());

//        ex3.forEach((codC,val) ->{
//            System.out.println("Cod capitol:" + codC + "\n");
//            System.out.println("Cod santier:" + val.keySet() + " Valoare: " + val.values());
//
//        });

        //wow ce simplu pt fiecare capitol pun in cod o noua mapa daca nu exista, daca ecista nu fac nimic,
        //iau mapa din ex3 de la cheia capitol si o actualizez punan in ea
        for (Capitol capitol :capitolList)
        {
            //If the key (capitol.getCod_capitol()) is not already present in the ex3 map,
            // putIfAbsent will insert it with a new empty HashMap as its value If the key is already present, it will do nothing.
            ex3.putIfAbsent(capitol.getCod_capitol(),new HashMap<>());
            // Retrieve the inner map (santierMap) associated with the current capitol's cod_capitol, Since putIfAbsent ensures that there's always a HashMap for
            // each cod_capitol santierMap will never be null.
            Map<Integer,Double> santierMap = ex3.get(capitol.getCod_capitol());//aici ia mapa
            santierMap.merge(capitol.getCod_santier(), capitol.getCantitate()* capitol.getPretUnitar(), Double::sum );
        }
        try(PrintWriter pw = new PrintWriter(new File("src/devize.txt")))
        {
            for (Map.Entry<Integer,Map<Integer,Double>> entry : ex3.entrySet())
            {
                pw.println("COd cap:" + entry.getKey());
                for(Map.Entry<Integer,Double> santierEntry : entry.getValue().entrySet())
                {
                    pw.println("Cod santier:" + santierEntry.getKey() + ", Val:" + santierEntry.getValue());
                }
                pw.println();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //cer4
        // Start server thread
        Thread serverThread = new Thread(new Server(santierList, 8080));
        serverThread.start();

        // Start client thread
        Thread clientThread = new Thread(new Client("localhost", 8080, 1)); // Example codSantier = 1
        clientThread.start();

        try {
            serverThread.join();
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//eom
}//eoc