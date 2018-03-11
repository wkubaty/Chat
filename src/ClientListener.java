import java.io.IOException;
import java.net.Socket;

public class ClientListener implements Runnable{
    private Server server;
    private Socket socket;


    public ClientListener(Server server){
        this.server = server;
    }

    @Override
    public void run() {
        try{
            socket = server.getServerSocket().accept();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}
