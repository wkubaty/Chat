import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UDPSender extends Thread{
    private DatagramSocket socket;
    private MessageBuffer messageBuffer;
    private ConcurrentLinkedQueue<Client> clients;

    public UDPSender(DatagramSocket UDPSocket, MessageBuffer messageBuffer, ConcurrentLinkedQueue<Client> clients) throws IOException{
        this.messageBuffer = messageBuffer;
        this.clients = clients;
        this.socket = UDPSocket;
    }

    @Override
    public void run(){
        while(true) {
            sendMessages();
        }
    }
    private void sendMessages() {
        try {
            ClientMessage clientMessage = messageBuffer.getClientMessage();
            for (Client client : clients) {
                if (!client.getClientSocketAddress().equals(clientMessage.getSocketAddress())) {
                    byte[] byteArray =  clientMessage.getMessage();
                    DatagramPacket sendPacket = new DatagramPacket(byteArray, byteArray.length, client.getClientSocketAddress());
                   // System.out.println("sending udp to: " + client.getClientSocketAddress());
                    socket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
