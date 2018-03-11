import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final int numberOfThreads = 2;
        final int port = 34567;
       // ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
       // LinkedList<ClientThread> clientsList = new LinkedList<>();
        try{
            Server server = new Server(port);
            server.run();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
}
