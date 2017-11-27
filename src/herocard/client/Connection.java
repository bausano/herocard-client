package herocard.client;

import herocard.events.Emitor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Comparator;
import java.util.PriorityQueue;
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
    
    /**
     * Socket communication connecting.
     */
    public Thread t;
    
    /**
     * Queue of requests to send.
     */
    public PriorityQueue<Request> queue;
    
    /**
     * 
     * @param host
     * @param port
     */
    public Connection(String host, Integer port) {
        this.host = host;
        
        this.port = port;
        
        this.queue = new PriorityQueue<>(Comparator.comparing(Request::getPriority));

        start();
    }
    
    /**
     * Starts a new thread for attempting the connection.
     */
    public void start() {
        t = new Thread(this);
        
        t.start();
    }

    /**
     * Attempts to connect to server every seconds until program is stopped or
     * connection established.
     */
    @Override
    public void run() {
        // TODO: Flag
        while (true) {
            try {
                if (! isConnected()) {
                    connect();

                    TimeUnit.SECONDS.sleep(1);
                }

                Request req = queue.poll();

                if (req == null) {
                    continue;
                }

                req.execute();

                //Emitor.dispatch("connected");
            } catch(IOException | InterruptedException ex) {
                Emitor.dispatch("disconnected");
            }
        }
    }
    
    public void spawn(Request req) {
        queue.add(req);
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
