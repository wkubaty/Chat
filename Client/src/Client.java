import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private final String fileName = "ascii.txt";
    private String host;
    private int port;
    private Socket TCPSocket;
    private DatagramSocket UDPSocket;
    private MulticastSocket multicastSocket;
    private String multicastAddress;
    private int multicastPort;

    public Client(String host, int port, String multicastAddress, int multicastPort) throws IOException{
        this.host = host;
        this.port = port;
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    public void run(){
        System.out.println("TCP/UDP CLIENT...");
        Scanner input = new Scanner(System.in);
        try{
            TCPSocket = new Socket(host, port);
            UDPSocket = new DatagramSocket(TCPSocket.getLocalPort());
            multicastSocket = new MulticastSocket(multicastPort);

            System.out.println("Your TCP socket's address (ID): " + TCPSocket.getLocalSocketAddress());
            System.out.println("Your UDP socket's address (ID): " + UDPSocket.getLocalSocketAddress());
            System.out.println("Your multicast socket's address (ID): " + multicastSocket.getLocalSocketAddress());
            TCPListener tcpListener = new TCPListener(TCPSocket);
            tcpListener.start();

            UDPListener udpListener = new UDPListener(UDPSocket);
            udpListener.start();

            MulticastListener multicastInputListener = new MulticastListener(multicastSocket, multicastAddress);
            multicastInputListener.start();

            while(true){
                String message = input.nextLine();
                if(message.equals("U")){
                    byte[] bytesArray = getDataFromFile(fileName);
                    DatagramPacket sendPacket = new DatagramPacket(bytesArray, bytesArray.length, InetAddress.getByName(host), port);
                    UDPSocket.send(sendPacket);
                    System.out.println("[INFO] Data has been sent");
                }
                else if(message.equals("M")){
                    byte[] bytesArray = getDataFromFile(fileName);
                    DatagramPacket sendPacket = new DatagramPacket(bytesArray, bytesArray.length, InetAddress.getByName(multicastAddress), multicastPort);
                    UDPSocket.send(sendPacket);
                    System.out.println("[INFO] Data has been sent");
                }
                else{
                    PrintWriter out = new PrintWriter(TCPSocket.getOutputStream(), true);
                    out.println(message);
                }
            }
        }
        catch (IOException e){
            System.out.println("Couldn't connect. Server is closed or too many connections. Try again in a while");
            System.out.println("You can only send multicast messages now");
            multicastModeOnly();

        }
        finally {
            UDPSocket.close();
            if(TCPSocket!=null){
                try{
                    TCPSocket.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void multicastModeOnly(){
        Scanner input = new Scanner(System.in);
        try{
            multicastSocket = new MulticastSocket(multicastPort);
            System.out.println("Your multicast socket's address (ID): " + multicastSocket.getLocalSocketAddress());
            MulticastListener multicastInputListener = new MulticastListener(multicastSocket, multicastAddress);
            multicastInputListener.start();
            while(true){
                String message = input.nextLine();
                if(message.equals("M")){
                    byte[] bytesArray = getDataFromFile(fileName);
                    DatagramPacket sendPacket = new DatagramPacket(bytesArray, bytesArray.length, InetAddress.getByName(multicastAddress), multicastPort);
                    DatagramSocket datagramSocket = new DatagramSocket();
                    datagramSocket.send(sendPacket);
                    datagramSocket.close();
                }
                else{
                    System.out.println("You are in multicast (only) mode. Type 'M' to send data");
                }
            }
        }
        catch (IOException e){
            System.out.println("Couldn't connect");

        }
        finally {
            multicastSocket.close();
        }
    }

    private byte[] getDataFromFile(String fileName) throws IOException{
        File file = new File(fileName);
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();
        return bytesArray;
    }
}
