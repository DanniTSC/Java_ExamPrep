import org.json.JSONArray;
import org.json.JSONTokener;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    List<Proiect> proiectList = new ArrayList<>();
    try(var fisier = new FileReader("src/proiecte.json"))
    {
        var array = new JSONArray(new JSONTokener(fisier));
        for (int i = 0; i < array.length(); i++) {
            var jsonObj = array.getJSONObject(i);
            Proiect p = new Proiect(
                jsonObj.getInt("cod_proiect"),
                jsonObj.getString("nume"),
                    jsonObj.getString("descriere"),
                    jsonObj.getString("manager"),
                    jsonObj.getFloat("buget")
            );
            proiectList.add(p);
        }

    }catch (Exception ex)
    {
        ex  .printStackTrace();
    }
    List<Cost> costList = new ArrayList<>();
    try{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("src/costuri.xml"));

        Element root = doc.getDocumentElement();
        NodeList nodeList = doc.getElementsByTagName("cost");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);

            int codCost = Integer.parseInt(elem.getElementsByTagName("cod_cost").item(0).getTextContent());
            int codProiect = Integer.parseInt(elem.getElementsByTagName("cod_proiect").item(0).getTextContent());
            String denumire = elem.getElementsByTagName("denumire_cost").item(0).getTextContent();
            String unitate = elem.getElementsByTagName("um").item(0).getTextContent();
            Float cantitate = Float.parseFloat(elem.getElementsByTagName("cantitate").item(0).getTextContent());
            Float pretUnitar = Float.parseFloat(elem.getElementsByTagName("pu").item(0).getTextContent());
            Cost cost = new Cost(codCost,codProiect,denumire,unitate,cantitate,pretUnitar);
            costList.add(cost);
        }

    }catch (Exception ex)
    {
        ex.printStackTrace();
    }
        //cer1 managerii si bugetul total estimat pt proiectele lor
        Map<String, Double> ex1 = new HashMap<>();
        for(Proiect proiect : proiectList)
        {
            String nume = proiect.getManager();
            Double val = proiect.getBuget();
            ex1.merge(nume,val,Double::sum);
        }
        ex1.entrySet().forEach(System.out::println);


        //cer2
        Map<Integer,Float> ex2 = new HashMap<>();
        for(Cost cost : costList)
        {
            int cod = cost.getCod_cost();
            Float val = cost.getCantitate();
            ex2.merge(cod,val,Float::sum);
        }
        ex2.entrySet().forEach(System.out::println);
        try(PrintWriter pw = new PrintWriter( new File("bugete.txt")))
        {
            ex2.entrySet().forEach(x->pw.println(x));

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //ex3
        Map<Integer, Map<Integer,Float>> ex3 = new HashMap<>();
        for(Cost cost : costList)
        {
            ex3.putIfAbsent(cost.getCod_cost(),new HashMap<>());
            Map<Integer,Float> mapaVal = ex3.get(cost.getCod_cost());
            mapaVal.merge(cost.getCod_proiect(),cost.getCantitate() * cost.getPretUnitar(), Float::sum);

        }
        ex3.entrySet().forEach(x-> System.out.println(x.getKey() + "\n" + x.getValue().entrySet()));

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("costuri");
            doc.appendChild(root);

            for(Map.Entry<Integer, Map<Integer,Float>> entry : ex3.entrySet())
            {
                int codCost = entry.getKey();
                Element costelement = doc.createElement("cod_cost");
                costelement.setAttribute("cod_cost",String.valueOf(codCost));
                for(Map.Entry<Integer,Float> subentry : entry.getValue().entrySet())
                {
                    int codPr = subentry.getKey();
                    float val = subentry.getValue();

                    Element projElem = doc.createElement("proiect");
                    costelement.appendChild(projElem);

                }
                root.appendChild(costelement);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("test.xml"));
            transformer.transform(source,result);

        }catch (Exception ex){
                ex.printStackTrace();
        }

        var serveThread = new Thread(new Server(costList,8080));
        serveThread.start();

        Thread clientThread = new Thread(new Client(8080,"localhost",1));
        clientThread.start();

        try {
            serveThread.join();
            clientThread.join();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }//eom
}//eoc