import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Produs> produsList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/Produse.txt")))
        {
            String linie;
            while((linie = bf.readLine()) != null)
            {
                Produs p = new Produs();
                String[] elem = linie.split(",");
                p.setCod_produs(Integer.parseInt(elem[0]));
                p.setNume(elem[1]);
                p.setPret(Double.parseDouble(elem[2]));
                produsList.add(p);
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        List<Tranzactie> tranzactieList = new ArrayList<>();
        try(var fisier = new FileReader("src/Tranzactii.json"))
        {
            var jsonArray = new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < jsonArray.length(); i++) {
                var jsonObj = jsonArray.getJSONObject(i);
                Tranzactie t = new Tranzactie(
                        jsonObj.getInt("codProdus"),
                        jsonObj.getInt("cantitate"),
                       TipTranzactie.valueOf(jsonObj.getString("tip").toUpperCase())
                );
                tranzactieList.add(t);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

//        //cer1 la conosla numarul de produse


        int nrproduse = 0;
        for(Produs produs : produsList)
        {
            for (Tranzactie tranzactie : tranzactieList)
            {
                if(produs.getCod_produs() == tranzactie.getCodProdus())
                {
                    nrproduse += tranzactie.getCantitate();
                }
            }
        }

        System.out.println("nr de produse este: " + nrproduse);

        //cer2 ord crescator numele
        produsList.stream().
                sorted((obj1, obj2) -> obj2.getNume().compareTo(obj1.getNume())).
                forEach(System.out::println);
        // cer 3
// 1. Calculăm numărul de tranzacții pentru fiecare produs
        Map<String, Long> nrTranzPerProdus = produsList.stream()
                .collect(Collectors.toMap(
                        Produs::getNume,  // Folosim metoda getDenumire pentru a obține denumirea produsului
                        produs -> tranzactieList.stream()
                                .filter(tranzactie -> tranzactie.getCodProdus() == produs.getCod_produs())
                                // Filtrăm tranzacțiile pentru a le găsi pe cele care corespund codului produsului
                                .count()  // Numărăm tranzacțiile pentru produsul respectiv
                ));

// 2. Sortăm produsele după numărul de tranzacții
        List<Map.Entry<String, Long>> produseSortate = nrTranzPerProdus.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())  // Sortăm intrările map-ului în ordine descrescătoare a valorilor (numărul de tranzacții)
                .collect(Collectors.toList());  // Colectăm rezultatul într-o listă

// 3. Scriem rezultatele în fișier
        try (PrintWriter pw = new PrintWriter(new File("src/lista.txt"))) {
            for (Map.Entry<String, Long> entry : produseSortate) {
                pw.println(entry.getKey() + ", " + entry.getValue() + " tranzactii");  // Scriem fiecare pereche cheie-valoare în fișier
            }
        } catch (Exception exception) {
            exception.printStackTrace();  // Tratăm excepțiile, dacă apar
        }
    //cerr4 val toatala a stocurilor
        // Calculăm valoarea totală a stocurilor
        float valTotala = (float) tranzactieList.stream()
                .filter(tranzactie -> tranzactie.getTipTranzactie() == TipTranzactie.INTRARE)  // Filtrăm tranzacțiile pentru a păstra doar cele de tip INTRARE
                .mapToDouble(tranzactie -> {  // Mapăm fiecare tranzacție la o valoare dublă reprezentând valoarea stocului (cantitate * preț)
                    Produs produs = produsList.stream()
                            .filter(p -> p.getCod_produs() == tranzactie.getCodProdus())  // Găsim produsul corespunzător tranzacției
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Produsul nu a fost găsit"));  // Aruncăm o excepție dacă produsul nu este găsit
                    return tranzactie.getCantitate() * produs.getPret();  // Calculăm valoarea stocului pentru această tranzacție
                })
                .sum();  // Sumăm toate valorile pentru a obține valoarea totală a stocurilor



// Afișăm valoarea totală a stocurilor la consolă
        System.out.println("Valoarea totală a stocurilor este: " + valTotala);

        //lamba practice
        int cod1 = 0;
        for(Produs produs : produsList)
        {
            for(Tranzactie tranzactie : tranzactieList)
            {
                if(produs.getCod_produs() == 1 && tranzactie.getCodProdus() == 1)
                {
                    cod1 += produs.getPret() * tranzactie.getCantitate();
                }
            }

        }
        System.out.println("pret: " + cod1);
        //cer1 stream
        double s1 = tranzactieList.stream() // Creează un stream din lista de tranzacții
                .filter(tranzactie -> tranzactie.getCodProdus() == 1) // Filtrează tranzacțiile pentru a păstra doar cele cu codul produsului egal cu 1
                .mapToDouble(tranzactie -> { // Map-ează fiecare tranzacție la valoarea stocului (cantitate * preț)
                    Produs produs = produsList.stream() // Creează un stream din lista de produse
                            .filter(p -> p.getCod_produs() == tranzactie.getCodProdus())
                            // Filtrează pentru a găsi produsul cu codul produsului egal cu cel al tranzacției
                            .findFirst() // Găsește primul produs care îndeplinește condiția
                            .orElseThrow(() -> new RuntimeException("Produsul nu a fost găsit")); // Aruncă o excepție dacă produsul nu este găsit
                    return tranzactie.getCantitate() * produs.getPret(); // Returnează valoarea stocului pentru tranzacția curentă
                })
                .sum(); // Sumează toate valorile stocului pentru a obține valoarea totală
        System.out.println(s1); // Afișează valoarea totală a stocului pentru produsul cu codul 1

//cer2
        produsList.stream() // Creează un stream din lista de produse
                .filter(produs -> produs.getPret() > 10) // Filtrează produsele pentru a păstra doar cele cu prețul mai mare de 10
                .forEach(System.out::println); // Afișează fiecare produs care îndeplinește condiția

//cer3 intrare si iesire
        double val = tranzactieList.stream() // Creează un stream din lista de tranzacții
                .filter(tranzactie -> tranzactie.getTipTranzactie().equals("INTRARE")) // Filtrează tranzacțiile pentru a păstra doar cele de tip "INTRARE"
                .mapToDouble(tranzactie -> { // Map-ează fiecare tranzacție la valoarea stocului (cantitate * preț)
                    Produs produs = produsList.stream() // Creează un stream din lista de produse
                            .filter(produs1 -> produs1.getCod_produs() == tranzactie.getCodProdus())
                            // Filtrează pentru a găsi produsul cu codul produsului egal cu cel al tranzacției
                            .findFirst() // Găsește primul produs care îndeplinește condiția
                            .orElseThrow(() -> new RuntimeException("Produsul nu a fost găsit")); // Aruncă o excepție dacă produsul nu este găsit
                    return tranzactie.getCantitate() * produs.getPret(); // Returnează valoarea stocului pentru tranzacția curentă
                })
                .sum(); // Sumează toate valorile stocului pentru a obține valoarea totală

//cer4
        System.out.println("CERINTA 4");
        produsList.stream() // Creează un stream din lista de produse
                .forEach(produs -> System.out.println(produs.getNume())); // Afișează numele fiecărui produs

//cer5
        produsList.stream() // Creează un stream din lista de produse
                .filter(produs -> produs.getPret() < 10) // Filtrează produsele pentru a păstra doar cele cu prețul mai mic de 10
                .forEach(System.out::println); // Afișează fiecare produs care îndeplinește condiția

        //cer6


    }//eom
}//eoc