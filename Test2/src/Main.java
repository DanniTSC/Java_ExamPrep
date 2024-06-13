import org.json.JSONArray;
import org.json.JSONTokener;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<Aventuri> aventuriList = new ArrayList<>();
        try(var fisier = new FileReader("src/aventuri.json")){
            var jsonArray = new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < jsonArray.length(); i++) {
                var jsonAventuri = jsonArray.getJSONObject(i);
                Aventuri aventuraJson = new Aventuri(
                        jsonAventuri.getInt("cod_aventura"),
                        jsonAventuri.getString("denumire"),
                        jsonAventuri.getFloat("tarif"),
                        jsonAventuri.getInt("locuri_disponibile")
                        );
            aventuriList.add(aventuraJson);
            }
        }catch(Exception e)
        {

            System.err.println(e);
        }
        //cer1
//        for(Aventuri aventura : aventuriList)
//        {
//            if(aventura.getLocuri_disponibile() < 20)
//            {
//                System.out.println("Aventura cu locuri disponibile sub 20 este " + aventura.getDenumire() + " si are " + aventura.getLocuri_disponibile() + " locuri disponibile ");
//            }
//        }
        //cer 2
        List<Rezervare> rezervareList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/rezervari.txt"))){
        String linie;
        while((linie = bf.readLine()) != null )
        {
            Rezervare r = new Rezervare();
            String[] elemente = linie.split(",");
            r.setId_rezervare(Integer.parseInt(elemente[0]));
            r.setCod_rezervare(Integer.parseInt(elemente[1]));
            r.setNr_locuri_rezervate(Integer.parseInt(elemente[2]));
            rezervareList.add(r);
        }
        }catch (Exception e){
            System.err.println(e);
        }
//        //am citit din fisier am tot in lista akm sa vedem
//        for(Aventuri aventura : aventuriList)
//        {
//            for(Rezervare rezervare : rezervareList)
//            {
//                if (aventura.getCod_aventura() == rezervare.getCod_rezervare())
//                {
//                    aventura.setLocuri_disponibile(aventura.getLocuri_disponibile() - rezervare.getNr_locuri_rezervate());
//                }
//            }
//        }
//        for(Aventuri aventura : aventuriList)
//        {
//            if( aventura.getLocuri_disponibile() > 5)
//            {
//                System.out.println("Aventura cu 5 loc dis dupa rezervare este: " + aventura.getDenumire() + " si mai are : " + aventura.getLocuri_disponibile() + " loc disp ");
//
//            }
//        }
        //cer 1 stream
        System.out.println("aventuri cu loc disp >= 20:");
        aventuriList.stream().filter(aventuri -> aventuri.getLocuri_disponibile() >= 20).forEach(System.out::println);
        //cer2
        Map<Integer, Integer> locuriRezervate = new HashMap<>();
        for(Rezervare rezervare : rezervareList)
        {
            locuriRezervate.put(rezervare.getCod_rezervare(),
                    locuriRezervate.getOrDefault(rezervare.getCod_rezervare(),0) + rezervare.getNr_locuri_rezervate());
        }
        System.out.println("Aventuri cu cel putin 5 locuri ramase:");
        aventuriList.stream().
        filter(aventuri -> aventuri.getLocuri_disponibile() - locuriRezervate.getOrDefault(aventuri.getCod_aventura(),0) >= 5).
                forEach(aventuri -> System.out.println("cod" + aventuri.getCod_aventura() + "nume" + aventuri.getDenumire() + "loc disp " + (aventuri.getLocuri_disponibile() - locuriRezervate.get(aventuri.getCod_aventura()))));
        //cer3
        try {
            PrintWriter pw = new PrintWriter(new File("src/venituri.txt"));
            // Sortarea aventurilor în ordinea alfabetică a denumirilor
            Collections.sort(aventuriList, (a1, a2) -> a1.getDenumire().compareTo(a2.getDenumire()));

            for (Aventuri aventura : aventuriList) {
                int locuriOcupate = 0;
                double sumaCastigata = 0.0;

                // Calcularea locurilor ocupate și a sumei câștigate pentru fiecare aventură
                for (Rezervare rezervare : rezervareList) {
                    if (rezervare.getCod_rezervare() == aventura.getCod_aventura()) {
                        locuriOcupate += rezervare.getNr_locuri_rezervate();
                    }
                }

                sumaCastigata = aventura.getTarif() * locuriOcupate;

                // Scrierea rezultatelor în fișier
                pw.write(aventura.getDenumire() + ", " + locuriOcupate + ", " + sumaCastigata + "\n");
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }//eom
}//eoc