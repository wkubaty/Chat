import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private MessageBuffer messageBuffer;
    private LinkedList<ClientListener> clients;
    private int port;
    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
        this.clients = new LinkedList<>();
    }
    public void run(){
        while(true){
            while (!messageBuffer.isEmpty()){
                ClientMessage clientMessage = messageBuffer.getClientMessage();
                SocketAddress socketAddress = clientMessage.getSocketAddress();
                String message = clientMessage.getMessage();

                for(ClientListener client: clients){
                    if(client.getClientSocketAddress()!=socketAddress){
                        client.addClientMessageToSend(clientMessage);

                    }

                }
            }
        }
    }
    public void addClient(ClientListener client){
        clients.add(client);
    }
    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void addMessageWithIDToBuffer(SocketAddress socketAddress, String message){
        messageBuffer.addMessageWithID(socketAddress, message);
    }


}
