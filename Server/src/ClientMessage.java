import java.net.SocketAddress;

public class ClientMessage {
    private SocketAddress socketAddress;
    private byte[] message;

    public ClientMessage(SocketAddress socketAddress, byte[] message){
        this.socketAddress = socketAddress;
        this.message = message;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public byte[] getMessage() {
        return message;
    }
}
