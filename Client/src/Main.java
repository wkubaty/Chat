import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Client client = new Client("localhost", 34567, "234.5.6.7", 12345);
            client.run();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

}
