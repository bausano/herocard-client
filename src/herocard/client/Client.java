package herocard.client;

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
     * Host name of a server.
     */
    private static String host;
    
    /**
     * Port a server is listening on.
     */
    private static Integer port;
    
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
     */
    public static void connect(String host, Integer port) {        
        Client.host = host;
        
        Client.port = port;
        
        Client.conn = new Connection(host, port);
    }
    
    /**
     * Attempts to revive the connection if the thread is dead.
     */
    public void onConnectionLost() {
        if (! conn.t.isAlive()) {
            Client.conn = new Connection(host, port);
        }
    }
}
