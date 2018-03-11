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
    private synchronized void sendMessages(){

        while(messageBuffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientMessage clientMessage = messageBuffer.getClientMessage();
        for (ClientThread client : clients) {
            if (!client.getClientSocketAddress().equals(clientMessage.getSocketAddress())) {
                PrintWriter out = client.getOut();
                if(out!=null){
                    out.println(clientMessage.getSocketAddress().toString() + " : " + clientMessage.getMessage());
                }
            }
        }
        notifyAll();
    }
    public synchronized void addMessage(){
        notifyAll();
    }

}
