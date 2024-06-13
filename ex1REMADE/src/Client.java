import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private  int port;
    private String host;
    private int cod;

    public Client(int port, String host, int cod) {
        this.port = port;
        this.host = host;
        this.cod = cod;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket(host,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            System.out.println("CLIENT CONECTAT");
            out.println(cod);
            String response = in.readLine();
            while(!(response = in.readLine()).equalsIgnoreCase("end"))
            {
                System.out.println("RESPONSS DE LA SERVER:" + response);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
