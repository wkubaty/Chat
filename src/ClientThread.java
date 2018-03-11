import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientThread extends Thread{
    private Server server;
    private Socket clientSocket;
    private ConcurrentLinkedQueue<ClientMessage> clientMessagesToSend;
    private PrintWriter out;
    public ClientThread(Server server, Socket clientSocket){
        this.server = server;
        this.clientSocket = clientSocket;
        this.clientMessagesToSend = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        try{
            while(true){
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msg = in.readLine();
                if(msg==null){
                    break;
                }
                System.out.println(clientSocket.getRemoteSocketAddress() + " : " + msg);
                server.addMessageWithIDToBuffer(clientSocket.getRemoteSocketAddress(), msg);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(clientSocket!=null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void addClientMessageToSend(ClientMessage clientMessage){
        clientMessagesToSend.add(clientMessage);
    }
    public SocketAddress getClientSocketAddress(){
        return clientSocket.getRemoteSocketAddress();
    }
    public PrintWriter getOut(){
        return out;
    }

}
