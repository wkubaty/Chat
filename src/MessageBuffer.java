import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBuffer{
    private ConcurrentLinkedQueue<ClientMessage> clientMessages;
    public MessageBuffer(){
        clientMessages = new ConcurrentLinkedQueue<>();
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
}
