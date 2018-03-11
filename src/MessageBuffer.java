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
    public synchronized void addClientMessage(SocketAddress socketAddress, String message){
        clientMessages.add(new ClientMessage(socketAddress, message));
        notifyAll();
    }
    public synchronized ClientMessage getClientMessage(){
        while (clientMessages.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientMessage clientMessage = clientMessages.poll();
        notifyAll();
        return clientMessage;
    }

    public void addClient(ClientThread clientThread){
        clients.add(clientThread);
    }
    public boolean isEmpty(){
        return clientMessages.isEmpty();
    }


}
