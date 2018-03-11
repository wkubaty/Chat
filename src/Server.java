import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {
    private ServerSocket serverSocket;
    private MessageBuffer messageBuffer;
    private ConcurrentLinkedQueue<ClientThread> clients;
    private MessageSender messageSender;
    private int port;
    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
        this.clients = new ConcurrentLinkedQueue<>();
        this.messageSender = new MessageSender(messageBuffer, clients);
    }
    public void run(){
        System.out.println("SERVER TCP...");
        ServerSocketListener serverSocketListener = new ServerSocketListener(this, serverSocket);
        serverSocketListener.start();
        messageSender.start();
    }

    public void addClient(ClientThread client){
        clients.add(client);
    }
    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void addMessageWithIDToBuffer(SocketAddress socketAddress, String message){
        messageBuffer.addClientMessage(socketAddress, message);
    //    messageSender.addMessage();
    }



}
