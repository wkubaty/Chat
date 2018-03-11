import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientListener implements Runnable{
    private Server server;
    private Socket clientSocket;
    private ConcurrentLinkedQueue<ClientMessage> clientMessagesToSend;

    public ClientListener(Server server){
        this.server = server;
        this.clientMessagesToSend = new ConcurrentLinkedQueue<>();

    }

    @Override
    public void run() {
        try{
            clientSocket = server.getServerSocket().accept();
            System.out.println("client: " + clientSocket.getPort() + " connected");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while(true){
                String msg = in.readLine();
                System.out.println("received message:" + msg + " from:" + clientSocket.getPort());
                server.addMessageWithIDToBuffer(clientSocket.getLocalSocketAddress(), msg);

                while(!clientMessagesToSend.isEmpty()){
                    ClientMessage message = clientMessagesToSend.peek();
                    out.println(message.getSocketAddress().toString() + " : " + message);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public void addClientMessageToSend(ClientMessage clientMessage){
        clientMessagesToSend.add(clientMessage);
    }
    public SocketAddress getClientSocketAddress(){
        return clientSocket.getRemoteSocketAddress();
    }
}
