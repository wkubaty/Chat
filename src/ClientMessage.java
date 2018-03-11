import java.net.SocketAddress;

public class ClientMessage {
    private SocketAddress socketAddress;
    private String message;

    public ClientMessage(SocketAddress socketAddress, String message){
        this.socketAddress = socketAddress;
        this.message = message;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public String getMessage() {
        return message;
    }
}
