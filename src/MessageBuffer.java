import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBuffer {
    private ConcurrentLinkedQueue<ClientMessage> clientMessages;

    public MessageBuffer(){
        clientMessages = new ConcurrentLinkedQueue<>();
    }

    public void addMessageWithID(SocketAddress socketAddress, String message){
        clientMessages.add(new ClientMessage(socketAddress, message));
    }
    public boolean isEmpty(){
        return clientMessages.isEmpty();
    }
    public ClientMessage getClientMessage(){
        return clientMessages.peek();
    }


}
