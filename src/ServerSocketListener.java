import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketListener extends Thread{
    private ServerSocket serverSocket;
    private Server server;

    public ServerSocketListener(Server server, ServerSocket serverSocket){
        this.server = server;
        this.serverSocket = serverSocket;
    }
    @Override
    public void run(){
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("new client: " + clientSocket.getRemoteSocketAddress() + " connected!");
                ClientThread clientThread = new ClientThread(server, clientSocket);
                server.addClient(clientThread);
                clientThread.start();
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
}
