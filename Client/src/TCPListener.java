import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPListener extends Thread{
    private Socket socket;

    public TCPListener(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            while(true){
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = in.readLine();
                if(response==null){
                    break;
                }
                System.out.println(response);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }
}

