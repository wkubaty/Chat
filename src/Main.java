import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) throws Exception {
      //  System.setProperty("java.net.preferIPv4Stack", "true");
        final int port = 34567;
        try{
            ConcurrentLinkedQueue<Client> clients = new ConcurrentLinkedQueue<>();
            Server server = new Server(port, clients, 3);
            server.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}
