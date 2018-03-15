import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPSender extends Thread{
    private MessageBuffer messageBuffer;
    private ConcurrentLinkedQueue<Client> clients;

    public TCPSender(MessageBuffer messageBuffer, ConcurrentLinkedQueue<Client> clients){
        this.messageBuffer = messageBuffer;
        this.clients = clients;
    }
    @Override
    public void run(){
        while(true){
            sendMessages();
        }
    }
    private void sendMessages(){
        ClientMessage clientMessage = messageBuffer.getClientMessage();
        for (Client client : clients) {
            if (!client.getClientSocketAddress().equals(clientMessage.getSocketAddress())) {
                PrintWriter out = client.getOut();
                if (out != null) {
                   // System.out.println("sending tcp to : " + clientMessage.getSocketAddress());
                    out.println(clientMessage.getSocketAddress() + " : " + new String(clientMessage.getMessage()));
                }
            }
        }
    }
}
