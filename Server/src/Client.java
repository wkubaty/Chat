import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class Client extends Thread{
    private MessageBuffer messageBuffer;
    private Socket clientSocket;
    private PrintWriter out;
    public Client(MessageBuffer messageBuffer, Socket clientSocket){
        this.messageBuffer = messageBuffer;
        this.clientSocket = clientSocket;
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
                messageBuffer.addClientMessage(new ClientMessage(clientSocket.getRemoteSocketAddress(), msg.getBytes()));
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

    public SocketAddress getClientSocketAddress(){
        return clientSocket.getRemoteSocketAddress();
    }
    public PrintWriter getOut(){
        return out;
    }

}
