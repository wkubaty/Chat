import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private MessageBuffer messageBuffer;
    private LinkedList<ClientThread> clients;
    private int port;
    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.messageBuffer = new MessageBuffer();
        this.clients = new LinkedList<>();
    }
    public void run(){
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(this, clientSocket);
                clientThread.start();

                while (!messageBuffer.isEmpty()) {
                    ClientMessage clientMessage = messageBuffer.getClientMessage();
                    String message = clientMessage.getMessage();
                    for (ClientThread client : clients) {
                        if (client.getClientSocketAddress() != clientMessage.getSocketAddress()) {
                            client.addClientMessageToSend(clientMessage);

                        }
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(serverSocket!=null){
                try{
                    serverSocket.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(ClientThread client){
        clients.add(client);
    }
    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void addMessageWithIDToBuffer(SocketAddress socketAddress, String message){
        messageBuffer.addMessageWithID(socketAddress, message);
    }


}
