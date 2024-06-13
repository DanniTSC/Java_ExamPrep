import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable {
    private List<Cost> costList;
    private int port;

    public Server(List<Cost> costList, int port) {
        this.costList = costList;
        this.port = port;
    }

    public void start(int port)
    {
        try(ServerSocket ss = new ServerSocket(port)){
            System.out.println("SERVER CONNECTAT");
            while(true)
            {
                try (Socket clientSock = ss.accept();
                     PrintWriter out = new PrintWriter(clientSock.getOutputStream(),true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()))){
                    System.out.println("SERVER CONN CLIENT");
                    int cod = Integer.parseInt(in.readLine());
                    Cost cost = costList.stream().filter(s -> s.getCod_cost() == cod).findFirst().orElse(null);
                    if(cost != null)
                    {
                        out.println(cost.getDenumire_cost());
                    }else {
                        System.out.println("COSTN OTFOUND");
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
    start(port);
    }
}
