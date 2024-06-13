import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable {
    private List<Santier> santierList;
private  int port;
    public Server(List<Santier> santierList, int port) {
        this.santierList = santierList;
        this.port = port;
    }
    public void run()
    {
        start(port);
    }

    public void start(int port)
    {
        try(ServerSocket serverSocket = new ServerSocket(port))
    {
        System.out.println("SERVER STARTED ON PORT:" + port);
        while (true)
        {
            try(Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
                {
                    System.out.println("SEVER connected to client...");
                    int codSanteir = Integer.parseInt(in.readLine());
                    System.out.println("SERVER Recieved cod santier" + codSanteir);

                    Santier santier = santierList.stream().filter(s->s.getCodSantier() == codSanteir).findFirst().orElse(null);
                    if(santier != null)
                    {
                        out.println(santier.getObiectiv());
                        System.out.println("SERVER serverul a trimis la client" + santier.getObiectiv());
                    }else {
                        out.println("santier not found");
                        System.out.println("SERVER santeir negasit");
                    }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }catch (Exception ex)
    {
        ex.printStackTrace();
    }

    }



}


