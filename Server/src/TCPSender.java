import java.util.concurrent.LinkedBlockingQueue;

public class TCPSender extends Thread{
    private MessageBuffer messageBuffer;
    private LinkedBlockingQueue<Client> clients;

    public TCPSender(MessageBuffer messageBuffer, LinkedBlockingQueue<Client> clients){
        this.messageBuffer = messageBuffer;
        this.clients = clients;
    }
    @Override
    public void run(){
        while(true){
            try {
                sendMessages();
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
    private void sendMessages() throws InterruptedException{
        ClientMessage clientMessage = messageBuffer.getClientMessage();
        String messageToSend = clientMessage.getSocketAddress() + " : " + new String(clientMessage.getMessage());

        clients.stream()
                .filter(client -> !client.getClientSocketAddress().equals(clientMessage.getSocketAddress()))
                .forEach(c -> c.getOut().println(messageToSend));

    }
}
