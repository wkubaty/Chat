import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final int numberOfThreads = 2;
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        LinkedList<ClientListener> clientsList = new LinkedList<>();
        for(ClientListener cl: clientsList){
            pool.submit(cl);
        }
    }
}
