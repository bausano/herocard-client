package herocard.client;
import java.io.IOException;

/**
 * Main network connection class.
 * 
 * @author michael
 */
public class Client {
    /**
     * Protocol handler.
     */
    private static Connection conn;
    
    /**
     * Creates a new instance of Message with current connection and given command.
     * 
     * @param command One of commands in server router.
     * @return
     */
    public static Message message(String command) {
        return new Message(new Request(conn), command);
    }
    
    /**
     * Boots a TCP socket on given host and port.
     * 
     * @param host Address of host server.
     * @param port Port on which host server is listening.
     * @throws java.io.IOException
     */
    public static void connect(String host, Integer port) throws IOException {
        Client.conn = new Connection(host, port);
    }
}
