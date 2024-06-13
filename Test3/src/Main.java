import org.json.JSONArray;
import org.json.JSONTokener;

import java.awt.image.ImageProducer;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.*;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Sectie> sectieList = new ArrayList<>();
        try(var fisier = new FileReader("src/sectii.json")){
            var jsonArray = (new JSONArray(new JSONTokener(fisier)));
            for (int i = 0; i < jsonArray.length(); i++) {
                var jsonObj = jsonArray.getJSONObject(i);
                Sectie s = new Sectie(
                  jsonObj.getInt("cod_sectie"),
                  jsonObj.getString("denumire"),
                  jsonObj.getInt("numar_locuri")
                );
                sectieList.add(s);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //cerr1
        sectieList.stream().filter(x -> x.getNumar_locuri() > 10).forEach(x -> System.out.println(x));


        //cer2
        List<Pacient> pacientList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/pacienti.txt"))){
            String linie;
            while((linie = bf.readLine()) != null)
            {
                String[] elem = linie.split(",");
                Pacient p = new Pacient();
                p.setCnp(Long.parseLong(elem[0]));
                p.setNume(elem[1]);
                p.setVarsta(Integer.parseInt(elem[2]));
                p.setCod_sectie(Integer.parseInt(elem[3]));

                pacientList.add(p);
            }
        }catch (Exception ex)
        {
            System.err.println(ex);
        }

//        Map<Integer, Double> varstaMediePeSectie = new HashMap<>();
//        Map<Integer, Integer> numarPacPeSectie = new HashMap<>();
//        for(Pacient pacient : pacientList)
//        {
//            int codSectie = pacient.getCod_sectie();
//            varstaMediePeSectie.put(codSectie,varstaMediePeSectie.getOrDefault(codSectie, 0.0) + pacient.getVarsta());
//            numarPacPeSectie.put(codSectie,numarPacPeSectie.getOrDefault(codSectie,0) + 1);
//
//        }
//
//        for(Map.Entry<Integer, Double> entry : varstaMediePeSectie.entrySet())
//        {
//            int codSectie = entry.getKey();
//            double totalVarsta = entry.getValue();
//            int nrPacienti = numarPacPeSectie.get(codSectie);
//        }
//
//        sectieList.sort((s1,s2) -> Double.compare(varstaMediePeSectie.getOrDefault(s2.getCod_sectie(),0.0), varstaMediePeSectie.getOrDefault(s1.getCod_sectie(),0.0)));
//
//        for(Sectie sectie : sectieList)
//        {
//            double varstaMedie = varstaMediePeSectie.getOrDefault(sectie.getCod_sectie(),0.0);
//            System.out.println(sectie + ",Vasta medie " + varstaMedie);
//        }
        // Declară un map pentru a stoca vârsta medie și secția
        Map<Double, Sectie> listaOrdonata = new HashMap<>();

// Parcurge fiecare secție din lista de secții
        sectieList.forEach(sectie -> {
            // Calculează vârsta medie a pacienților pentru secția curentă
            OptionalDouble optionalVarstaMedie = pacientList.stream()
                    // Filtrează pacienții pentru a obține doar cei care aparțin secției curente
                    .filter(pacient -> pacient.getCod_sectie() == sectie.getCod_sectie())
                    // Extrage vârsta fiecărui pacient ca un stream de int
                    .mapToInt(Pacient::getVarsta)
                    // Calculează media vârstelor
                    .average();

            // Dacă există o vârstă medie calculată, o adaugă în map împreună cu secția curentă
            optionalVarstaMedie.ifPresent(varstaMedie -> listaOrdonata.put(varstaMedie, sectie));
        });

// Sortează map-ul după cheile (vârstele medii) în ordine descrescătoare
        listaOrdonata.keySet().stream()
                .sorted(Comparator.reverseOrder())
                // Pentru fiecare cheie (vârstă medie) sortată
                .forEach(varstaMedie -> {
                    // Obține secția corespunzătoare vârstei medii din map
                    Sectie sectie = listaOrdonata.get(varstaMedie);
                    // Afișează secția și vârsta medie
                    System.out.println("sectia: " + sectie + " ARE VARSTA MEDIE " + varstaMedie);
                });
        //cer 3 scriere text
        try(PrintWriter pw = new PrintWriter(new File("src/junral.txt"))) {
            sectieList.stream().forEach(sectie -> {
            long nrPacienti = pacientList.stream().filter(pacient -> pacient.getCod_sectie() == sectie.getCod_sectie()).count();
            pw.write("Nume sectie" + sectie.getDenumire() + " Cod sectie" + sectie.getCod_sectie() + " Nr Pacienti " + nrPacienti + "\n");
            });
            pw.close();

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //cerr4
        // Creăm un nou fir (thread) pentru server
        var server = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket server = new ServerSocket(8080)) {
                    System.out.println("[SERVER] Se așteaptă conexiunea cu clientul...");

                    // Așteaptă conexiunea de la client
                    try (Socket client = server.accept()) {
                        System.out.println("[SERVER] Am realizat conexiunea cu clientul...");
                        var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        var out = new PrintWriter(client.getOutputStream(), true);

                        // Citește codul secției de la client
                        var cod = Integer.parseInt(in.readLine());
                        System.out.println("[SERVER] Am primit următorul cod " + cod);

                        // Calculează numărul de pacienți din secția respectivă
                        long numarPacienti = pacientList.stream().filter(pacient -> pacient.getCodSectie() == cod).count();
                        // Calculează numărul de locuri libere în secție
                        var numarLocuriLibere = sectieList.stream().filter(sectie -> sectie.getCodSectie() == cod).findFirst().get().getNumarLocuri() - numarPacienti;

                        // Trimite numărul de locuri libere către client
                        out.println(numarLocuriLibere);
                        System.out.println("[SERVER] Am transmis următorul număr de locuri libere " + numarLocuriLibere);
                        System.out.println("[SERVER] Am încheiat conexiunea");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Creăm un nou fir (thread) pentru client
        var client = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Socket client = new Socket("localhost", 8080)) {
                    System.out.println("[CLIENT] M-am conectat la server...");
                    var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    var out = new PrintWriter(client.getOutputStream(), true);

                    // Trimite codul secției către server
                    var cod = 1;
                    out.println(cod);
                    System.out.println("[CLIENT] Am transmis următorul cod " + cod);

                    // Primește numărul de locuri libere de la server
                    var nrLocuri = Integer.parseInt(in.readLine());
                    System.out.println("[CLIENT] Am primit următorul număr de locuri libere " + nrLocuri);
                    System.out.println("[CLIENT] Am încheiat conexiunea cu server-ul");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Pornim firele de execuție pentru server și client
        server.start();
        client.start();

        // Așteptăm terminarea firelor de execuție
        server.join();
        client.join();
    }
}




    }//eom
    }//eoc