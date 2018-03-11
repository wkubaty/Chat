import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class Server {
    private ServerSocket serverSocket;
    private int port;
    private MessageBuffer messageBuffer;
    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
    }
    public ServerSocket getServerSocket(){
        return serverSocket;
    }
    public void addMessageWithIDToBuffer(SocketAddress socketAddress, String message){
        messageBuffer.addMessageWithID(socketAddress, message);
    }
}
