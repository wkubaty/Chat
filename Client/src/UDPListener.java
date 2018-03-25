import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPListener extends Thread{
    private final int maxDataSize = 1024;
    private DatagramSocket UDPSocket;

    public UDPListener(DatagramSocket UDPSocket){
        this.UDPSocket = UDPSocket;
    }

    @Override
    public void run(){
        try {
            byte[] receiveBuffer = new byte[maxDataSize];
            while(true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);
                UDPSocket.receive(receivePacket);
                System.out.println("[INFO] Received UDP data from: " + receivePacket.getSocketAddress());
                /*
                System.out.println((new String(receiveBuffer)).trim());
                */
                FileOutputStream out = new FileOutputStream("file.txt");
                out.write(receiveBuffer, 0, receiveBuffer.length);
                out.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
