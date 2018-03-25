import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSocketListener extends Thread{
    private Server server;
    private int numberOfClients;
    private MessageBuffer messageBuffer;
    public ServerSocketListener(Server server, int numberOfClients, MessageBuffer messageBuffer){
        this.server = server;
        this.numberOfClients = numberOfClients;
        this.messageBuffer = messageBuffer;
    }
    @Override
    public void run(){
        try {
            ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfClients);
            while (true) {
                if(pool.getActiveCount()<numberOfClients){
                    if(server.getServerSocket().isClosed()){
                        server.reopenServerSocket();
                    }
                    Socket clientSocket = server.getServerSocket().accept();
                    System.out.println("new client: " + clientSocket.getRemoteSocketAddress() + " connected!");

                    Client client = new Client(messageBuffer, clientSocket);
                    pool.execute(client);
                    server.addClient(client);
                }
                else{
                    server.closeServerSocket();
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
