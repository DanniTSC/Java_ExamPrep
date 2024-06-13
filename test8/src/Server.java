import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Server implements Runnable {
    private List<Programare> programareList;
    private List<Disciplina> disciplinaList;
    private int port;

    public Server(List<Programare> programareList, List<Disciplina> disciplinaList, int port) {
        this.programareList = programareList;
        this.disciplinaList = disciplinaList;
        this.port = port;
    }

    @Override
    public void run() {
        start(port);
    }

    public void start(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("SERVER STARTED ON PORT: " + port);
            try (Socket clientSock = ss.accept();
                 PrintWriter out = new PrintWriter(clientSock.getOutputStream(), true); // auto-flush
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()))) {

                System.out.println("SERVER TO CLIENT CONN");
                String numeForm = in.readLine();
                System.out.println("SERVER A PRIMIT " + numeForm);

                List<Programare> listaFac = programareList.stream()
                        .filter(s -> s.getFacultate().equalsIgnoreCase(numeForm.trim()))
                        .collect(Collectors.toList());

                List<String> lastEX = listaFac.stream().map(programare -> {
                    String numeDis = disciplinaList.stream()
                            .filter(disciplina -> disciplina.getCodDisciplina() == programare.getCod())
                            .map(Disciplina::getNume)
                            .findFirst()
                            .orElse("nume mort");
                    return "Zi: " + programare.getZi() + ", Sala: " + programare.getSala() + ", Ora: " + programare.getOra() + ", Disciplina: " + numeDis;
                }).collect(Collectors.toList());

                for (String rez : lastEX) {
                    out.println(rez);
                }
                out.println("END");
                out.flush(); // forțăm trimiterea datelor
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
