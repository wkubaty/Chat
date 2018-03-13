import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private ConcurrentLinkedQueue<Client> clients;
    private MessageBuffer messageBuffer;
    private int port;
    private int numberOfClients;
    public Server(int port, ConcurrentLinkedQueue<Client> clients, int numberOfClients) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
        this.clients = clients;
        this.numberOfClients = numberOfClients;
    }
    @Override
    public void run(){
        try{
            System.out.println("SERVER IS RUNNING...");
            MessageBuffer TCPmessageBuffer = new MessageBuffer();
            ServerSocketListener serverSocketListener = new ServerSocketListener(this, numberOfClients,TCPmessageBuffer);
            serverSocketListener.start();
            TCPSender tcpSender = new TCPSender(TCPmessageBuffer, clients);
            tcpSender.start();

            MessageBuffer UDPmessageBuffer = new MessageBuffer();
            DatagramSocket UDPSocket = new DatagramSocket(port);
            UDPListener udpListener = new UDPListener(UDPSocket, UDPmessageBuffer);
            udpListener.start();

            UDPSender udpSender = new UDPSender(UDPSocket, UDPmessageBuffer, clients);
            udpSender.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    public void addClient(Client client){
        clients.add(client);
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void addClientMessageToBuffer(ClientMessage clientMessage){

        messageBuffer.addClientMessage(clientMessage);
    }

    public void reopenServerSocket(){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeServerSocket(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
