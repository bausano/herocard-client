package herocard.client;

import herocard.events.Connected;
import herocard.events.Disconnected;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * This class should be exported to an interface.
 *
 * @author michael
 */
public final class Connection implements Runnable {
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
    
    public Thread t;
    
    /**
     * 
     * @param host
     * @param port
     */
    public Connection(String host, Integer port) {
        this.host = host;
        
        this.port = port;

        attempToConnect();
    }
    
    /**
     * Starts a new thread for attempting the connection.
     */
    public void attempToConnect() {
        t = new Thread(this);
        
        t.start();
    }

    /**
     * Attempts to connect to server every seconds until program is stopped or
     * connection established.
     */
    @Override
    public void run() {
        do {
            try {
                connect();
                
                if (! isConnected()) {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (IOException | InterruptedException ex) {
                Disconnected.event().trigger();
            }
        } while(! isConnected());
        
        Connected.event().trigger();
    }
    
    /**
     * Establishes a connection with the server.
     * 
     * @throws java.io.IOException
     */
    private void connect() throws IOException {
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
    
    /**
     * 
     * @return True if connection with server is established. 
     */
    public Boolean isConnected() {
        if (socket == null) {
            return false;
        }
        
        return socket.isConnected();
    }
}
