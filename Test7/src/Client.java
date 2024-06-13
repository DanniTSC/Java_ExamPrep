import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements  Runnable{
private int port;
private  String host;
private int cod;

    public Client(int port, String host, int cod) {
        this.port = port;
        this.host = host;
        this.cod = cod;
    }


    @Override
    public void run() {
        try(Socket socketClient = new Socket(host,port);
            PrintWriter out = new PrintWriter(socketClient.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream())))
        {
            System.out.println("CLIENT OCNTEC");
            out.println(cod);
            String response = in.readLine();
            System.out.println("CLIENT RASPUNS" + response);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
