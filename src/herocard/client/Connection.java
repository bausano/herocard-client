package herocard.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author michael
 */
public class Connection {
    /**
     * TCP socket.
     */
    private Socket socket;
    
    /**
     * Host server.
     */
    private final String host;
    
    /**
     * Port the host server is listening on.
     */
    private final Integer port;
    
    /**
     * 
     * @param host
     * @param port
     * @throws IOException 
     */
    public Connection(String host, Integer port) throws IOException {
        this.host = host;
        
        this.port = port;
        
        this.init();
    }
    
    /**
     * Establishes a connection with the server.
     * 
     * @throws IOException 
     */
    private void init() throws IOException {
        socket = new Socket(host, port);

        socket.setKeepAlive(true);
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public OutputStream out() throws IOException {
        return socket.getOutputStream();
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public InputStream in() throws IOException {
        return socket.getInputStream();
    }
}
