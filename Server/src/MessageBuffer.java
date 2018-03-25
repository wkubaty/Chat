import java.util.concurrent.LinkedBlockingQueue;

public class MessageBuffer{
    private LinkedBlockingQueue<ClientMessage> clientMessages;

    public MessageBuffer(){
        clientMessages = new LinkedBlockingQueue<>();
    }

    public void addClientMessage(ClientMessage clientMessage){
        clientMessages.add(clientMessage);
    }

    public ClientMessage getClientMessage() throws InterruptedException{
        return clientMessages.take();
    }
}
