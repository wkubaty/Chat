import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
      //  System.setProperty("java.net.preferIPv4Stack", "true");
        final int port = 34567;
        try{
            Server server = new Server(port, 3);
            server.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}
