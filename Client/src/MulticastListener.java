import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class MulticastListener extends Thread{
    private final int maxDataSize = 1024;
    private MulticastSocket multicastSocket;
    private String multicastAddress;


    public MulticastListener(MulticastSocket multicastSocket, String multicastAddress) {
        this.multicastSocket = multicastSocket;
        this.multicastAddress = multicastAddress;
    }

    @Override
    public void run(){
        try {
            multicastSocket.joinGroup(InetAddress.getByName(multicastAddress));
            byte[] receiveBuffer = new byte[maxDataSize];
            while(true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                multicastSocket.receive(receivePacket);
                System.out.println("[INFO] Received multicast data from: " + receivePacket.getSocketAddress());
                /*
                String msg = new String(receivePacket.getData(), receivePacket.getOffset(),
                        receivePacket.getLength());
                System.out.println(msg.trim());
                */
                FileOutputStream out = new FileOutputStream("file.txt");
                out.write(receiveBuffer, 0, receiveBuffer.length);
                out.close();

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                multicastSocket.leaveGroup(InetAddress.getByName(multicastAddress));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
