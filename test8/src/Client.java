import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private String nume;
    private String host;
    private int port;

    public Client(String nume, String host, int port) {
        this.nume = nume;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("CLIENT CONECTAT LA SERVER");
            out.println(nume);
            System.out.println("Client a trimis: " + nume);

            String response;
            while (!(response = in.readLine()).equals("END")) {
                System.out.println("RASPUNS CLIENT: " + response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
