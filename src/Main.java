import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        try{
            Server server = new Server(34567, 3);
            server.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

}
