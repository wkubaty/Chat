import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private LinkedBlockingQueue<Client> clients;
    private MessageBuffer messageBuffer;
    private int port;
    private int numberOfClients;
    private DatagramSocket udpSocket;
    public Server(int port, int numberOfClients) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
        this.clients = new LinkedBlockingQueue<>();
        this.numberOfClients = numberOfClients;
    }
    @Override
    public void run(){
        try{
            System.out.println("SERVER IS RUNNING...");
            MessageBuffer tcpMessageBuffer = new MessageBuffer();
            ServerSocketListener serverSocketListener = new ServerSocketListener(this, numberOfClients, tcpMessageBuffer);
            serverSocketListener.start();
            TCPSender tcpSender = new TCPSender(tcpMessageBuffer, clients);
            tcpSender.start();

            MessageBuffer udpMessageBuffer = new MessageBuffer();
            udpSocket = new DatagramSocket(port);
            UDPListener udpListener = new UDPListener(udpSocket, udpMessageBuffer);
            udpListener.start();

            UDPSender udpSender = new UDPSender(udpSocket, udpMessageBuffer, clients);
            udpSender.start();

            tcpSender.join();
            udpSender.join();
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        finally {
            udpSocket.close();

            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
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
