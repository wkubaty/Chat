import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final int numberOfThreads = 2;
        final int port = 23456;
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        LinkedList<ClientListener> clientsList = new LinkedList<>();
        try{
            Server server = new Server(port);
            clientsList.add(new ClientListener(server));
            for(ClientListener cl: clientsList){
                pool.submit(cl);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
}
