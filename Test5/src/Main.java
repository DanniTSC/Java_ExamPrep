import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Carte> carteList = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/carti.txt")))
        {
            String linie;
            while((linie = bf.readLine()) != null)
            {
                Carte c = new Carte();
                String[] elem = linie.split("\t");
                c.setCota(elem[0]);
                c.setTitlu(elem[1]);
                c.setAutor(elem[2]);
                c.setAnLansare(Integer.parseInt(elem[3]));
                carteList.add(c);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Imprumut> imprumutList = new ArrayList<>();
        try(var con = DriverManager.getConnection("jdbc:sqlite:src/biblioteca.db");
        var cmd = con.createStatement();
        var response = cmd.executeQuery("SELECT * from imprumuturi")){
        while(response.next()){
            Imprumut imprumut = new Imprumut(
                    response.getString(1),
                    response.getString(2),
                    response.getInt(3)
            );
            imprumutList.add(imprumut);
        }

        }catch (Exception ex) {
        ex.printStackTrace();
        }
            //cer1
        carteList.stream().filter(carte -> carte.getAnLansare() < 1940).sorted((o1, o2) -> o1.getTitlu().compareTo(o2.getTitlu())).forEach(System.out::println);
        //cer2
        imprumutList.stream().map(Imprumut::getNume).distinct().forEach(System.out::println);
        //cer3
        Map<String, Integer> totalZileImpr = imprumutList.stream().collect(Collectors.groupingBy(Imprumut::getNume, Collectors.summingInt(Imprumut::getNrZile)));
        totalZileImpr.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed()).forEach(System.out::println);
    }//eom
}//eoc