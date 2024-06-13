import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        List<Disciplina> disciplinaList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(new File("src/S16_discipline.txt")))){
            String linie ;
            while((linie = bf.readLine()) != null) {
                String[] elem = linie.split(",");
                Disciplina d = new Disciplina();
                d.setCodDisciplina(Integer.parseInt(elem[0]));
                d.setNume(elem[1]);
                d.setOptionalitate(elem[2]);
                disciplinaList.add(d);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        List<Programare> programareList = new ArrayList<>();
        try(var con = DriverManager.getConnection("jdbc:sqlite:src/S16_programari.db");
        var stm = con.createStatement();
        var res = stm.executeQuery("SELECT  * from Programari");)
        {
            while  (res.next())
            {
                Programare p = new Programare(
                  res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5)
                );
                programareList.add(p);
            }


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //cer1
      List<Disciplina> listaOp =  disciplinaList.stream().filter(disciplina -> disciplina.getOptionalitate().equalsIgnoreCase("optionala")).collect(Collectors.toList());
        listaOp.forEach(System.out::println);
        //cer2
       List<Programare> vineri =
        programareList.stream().filter(programare ->
                disciplinaList.stream().anyMatch(disciplina -> disciplina.getCodDisciplina() == programare.getCod())
    ).collect(Collectors.toList());
        vineri.stream().filter(vineri1 -> vineri1.getZi().equalsIgnoreCase("vineri")).sorted((o1, o2) -> o1.getSala().compareTo(o2.getSala())).forEach(System.out::println);
//
//        //cer3
//        String numeSala;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("SALA:");
//        numeSala = scanner.nextLine().trim();
//        //zi interval disciplina formatie
//        //disciplina din alalatu
//        List<Programare> listaNume =
//        programareList.stream().filter(programare ->
//                disciplinaList.stream().anyMatch(disciplina -> disciplina.getCodDisciplina() == programare.getCod())).collect(Collectors.toList());
//        //List<Programare> ex5 =
//                listaNume.stream().filter(programare -> programare.getSala().equalsIgnoreCase(numeSala.trim())).forEach(System.out::println);

                //CHAT_________________
//        List<String> vineri = programareList.stream()
//                .filter(programare -> programare.getZi().equalsIgnoreCase("vineri"))
//                .sorted((o1, o2) -> o1.getSala().compareTo(o2.getSala()))
//                .map(programare -> {
//                    String numeDisciplina = disciplinaList.stream()
//                            .filter(disciplina -> disciplina.getCodDisciplina() == programare.getCod())
//                            .map(Disciplina::getNume)
//                            .findFirst()
//                            .orElse("Nume necunoscut");
//                    return "Zi: " + programare.getZi() + ", Interval: " + programare.getOra() +
//                            ", Sala: " + programare.getSala() + ", Disciplina: " + numeDisciplina;
//                })
//                .collect(Collectors.toList());
//
//        // Afișează lista de cursuri din ziua de vineri
//        vineri.forEach(System.out::println);
//
//        // Cerința 3: Filtrare după numele sălii
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Introduceți numele sălii: ");
//        String numeSala = scanner.nextLine().trim();
//
//        List<String> listaNume = programareList.stream()
//                .filter(programare -> disciplinaList.stream()
//                        .anyMatch(disciplina -> disciplina.getCodDisciplina() == programare.getCod()))
//                .filter(programare -> programare.getSala().equalsIgnoreCase(numeSala))
//                .map(programare -> {
//                    String numeDisciplina = disciplinaList.stream()
//                            .filter(disciplina -> disciplina.getCodDisciplina() == programare.getCod())
//                            .map(Disciplina::getNume)
//                            .findFirst()
//                            .orElse("Nume necunoscut");
//                    return "Zi: " + programare.getZi() + ", Interval: " + programare.getOra() +
//                            ", Sala: " + programare.getSala() + ", Disciplina: " + numeDisciplina;
//                })
//                .collect(Collectors.toList());
//
//        // Afișează lista de programări pentru sala specificată
//        listaNume.forEach(System.out::println);
        // EU refacut
        List<Programare> listaOrd =
                programareList.stream().
                        filter(programare -> programare.getZi().
                                equalsIgnoreCase("vineri"))
                        .sorted((o1, o2) -> o1.getSala().compareTo(o2.getSala())).collect(Collectors.toList());


        List<String> listatest = listaOrd.stream().map(programare -> {
           String numeDisc = disciplinaList.stream().filter(
                   disciplina -> disciplina.getCodDisciplina() == programare.getCod()
           ) .map(Disciplina::getNume).findFirst().orElse("numeMort");
            return "Zi: " + programare.getZi() + ", Sala: " + programare.getSala() + ", Ora: " + programare.getOra() + ", Disciplina: " + numeDisc;
        }).collect(Collectors.toList());
        listatest.forEach(System.out::println);

        String numesala = "A102";

        //zi interval disciplina formatie
        List<Programare> listaSala =
                programareList.stream().filter(programare -> programare.getSala().equalsIgnoreCase(numesala)).collect(Collectors.toList());
        List<String> listaex5 =
                listaSala.stream().map(programare -> {
                    String numeDis = disciplinaList.stream().filter(
                            disciplina -> disciplina.getCodDisciplina() == programare.getCod()
                    ).map(Disciplina::getNume).findFirst().orElse("nu s agasit");
                    return "Zi: " + programare.getZi() + ", Interval: " + programare.getOra() +
                            ", Sala: " + programare.getSala() + ", Disciplina: " + numeDis;
                }).collect(Collectors.toList());
            listaex5.forEach(System.out::println);

    Thread serverThread = new Thread(new Server(programareList,disciplinaList,8080));
    serverThread.start();
    Thread clientThread = new Thread(new Client("Informatica","localhost",8080));
    clientThread.start();
    try{
        serverThread.join();;
        clientThread.join();
    }catch (Exception ex) {
    ex.printStackTrace();
    }

    }//eom
}//eoc