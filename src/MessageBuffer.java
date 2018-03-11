import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBuffer{
    private ConcurrentLinkedQueue<ClientMessage> clientMessages;
    private LinkedList<ClientThread> clients;
    public MessageBuffer(){
        clientMessages = new ConcurrentLinkedQueue<>();
        clients = new LinkedList<>();
    }
    public void addMessageWithID(SocketAddress socketAddress, String message){
        clientMessages.add(new ClientMessage(socketAddress, message));
    }
    public ClientMessage getClientMessage(){
        return clientMessages.poll();
    }
    public void addClient(ClientThread clientThread){
        clients.add(clientThread);
    }
    public boolean isEmpty(){
        return clientMessages.isEmpty();
    }


}
