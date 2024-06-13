import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private String host;
    private int port;
    private int codSantier;

    public Client(String host, int port, int codSantier) {
        this.host = host;
        this.port = port;
        this.codSantier = codSantier;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("CLIENT connected to server");
            out.println(codSantier);
            System.out.println("CLIENT sent cod santier: " + codSantier);

            String response = in.readLine();
            System.out.println("CLIENT received response: " + response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
