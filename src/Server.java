import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;
    private int port;

    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }
    public ServerSocket getServerSocket(){
        return serverSocket;
    }
}
