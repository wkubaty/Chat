import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBuffer{
    private ConcurrentLinkedQueue<ClientMessage> clientMessages;
    private LinkedList<Client> clients;
    public MessageBuffer(){
        clientMessages = new ConcurrentLinkedQueue<>();
        clients = new LinkedList<>();
    }
    public synchronized void addClientMessage(ClientMessage clientMessage){
        clientMessages.add(clientMessage);
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
        return clientMessages.poll();

    }

    public void addClient(Client client){
        clients.add(client);
    }
    public boolean isEmpty(){
        return clientMessages.isEmpty();
    }


}
