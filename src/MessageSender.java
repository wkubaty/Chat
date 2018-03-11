import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSender extends Thread{
    private MessageBuffer messageBuffer;
    private ConcurrentLinkedQueue<ClientThread> clients;
    public MessageSender(MessageBuffer messageBuffer, ConcurrentLinkedQueue<ClientThread> clients){
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
        for (ClientThread client : clients) {
            if (!client.getClientSocketAddress().equals(clientMessage.getSocketAddress())) {
                PrintWriter out = client.getOut();
                if(out!=null){
                    out.println(clientMessage.getSocketAddress().toString() + " : " + clientMessage.getMessage());
                }
            }
        }
        //notifyAll();
    }


}
