
import java.net.*;
import java.util.Arrays;

public class UDPListener extends Thread{
    private final int maxDataSize = 1024;
    private DatagramSocket socket;
    private MessageBuffer messageBuffer;

    public UDPListener(DatagramSocket socket, MessageBuffer messageBuffer){
        this.socket = socket;
        this.messageBuffer = messageBuffer;
    }
    @Override
    public void run(){
        try {

            while(true) {
                byte[] receiveBuffer = new byte[maxDataSize];
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                /*
                System.out.println(receivePacket.getSocketAddress() + " : ");
                System.out.println((new String(receiveBuffer)).trim());
                */
                SocketAddress socketAddress = receivePacket.getSocketAddress();
                messageBuffer.addClientMessage(new ClientMessage(socketAddress, receivePacket.getData()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (socket != null){
                socket.close();
            }
        }
    }
}
