import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Server implements Runnable{
    private int port;
    private List<Persona> personaList;
    private List<Tranzactie> tranzactieList;

    public Server(int port, List<Persona> personaList, List<Tranzactie> tranzactieList) {
        this.port = port;
        this.personaList = personaList;
        this.tranzactieList = tranzactieList;
    }
        public  void start(int port)
        {
            try(ServerSocket ss = new ServerSocket(port)){
                System.out.println("SERVER CONN");
                try(Socket client = ss.accept();
                    PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                    System.out.println("CLIENT CONN LA SERVER");
                    int cod = Integer.parseInt(in.readLine());
                    System.out.println("COD PRIMIT" + cod);
                    Tranzactie tranzactie = tranzactieList.stream()
                            .filter(t -> t.getCod() == cod)
                            .findFirst()
                            .orElse(null);

                    if (tranzactie != null) {
                        // Find the corresponding person
                        Persona persona = personaList.stream()
                                .filter(p -> p.getCod() == tranzactie.getCod())
                                .findFirst()
                                .orElse(null);

                        if (persona != null) {
                            out.println(persona.getNume());
                            System.out.println("Sent to client: " + persona.getNume());
                        } else {
                            out.println("No person found for this tranzactie cod");
                            System.out.println("No person found for this tranzactie cod");
                        }
                    } else {
                        out.println("No tranzactie found for this cod");
                        System.out.println("No tranzactie found for this cod");
                    }
                    out.println("END");

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    @Override
    public void run() {
        start( port);
    }
}
