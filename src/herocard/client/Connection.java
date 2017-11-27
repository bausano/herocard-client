package herocard.client;

import herocard.events.Emitter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This class should be exported to an interface.
 *
 * @author michael
 */
public final class Connection implements Runnable {
    /**
     * Socket communication connecting.
     */
    public Thread t;
    
    /**
     * Queue of requests to send.
     */
    public volatile PriorityBlockingQueue<Request> queue = new PriorityBlockingQueue();
    
    /**
     * Is true if last request was successful, otherwise false.
     */
    public Boolean established = false;
    
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
     * Flag breaking the cycle.
     */
    private Boolean running = true;
    
    /**
     * 
     * @param host
     * @param port
     */
    public Connection(String host, Integer port) {
        this.host = host;
        
        this.port = port;

        start();
    }
    
    /**
     * Starts a new thread for connection.
     */
    public void start() {
        t = new Thread(this);
        
        t.start();
    }

    /**
     * Connects to the server and handles communication.
     */
    @Override
    public void run() {
        try {
            // If the socket seems not to be connected to the server,
            // attempt to establish a connection.
            if (! isConnected()) {
                TimeUnit.SECONDS.sleep(1);

                connect();

                Emitter.dispatch("connected");
            }

            // Pops a request from the blocking queue.
            Request req = queue.take();

            if (req == null) {
                return;
            }

            req.execute();
        } catch(IOException | InterruptedException ex) {
            established = false;

            Emitter.dispatch("disconnected");
        }
        
        // Loops the method.
        if (running) {
            run();
        }
    }
    
    /**
     * Adds a new request to the executing queue.
     * 
     * @param req 
     */
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
        
        established = true;
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
        
        if (! socket.isConnected()) {
            return false;
        }
        
        return established;
    }
    
    /**
     * Breaks the connection.
     */
    public void disconnect() {
        running = false;
        
        try {
            socket.close();
        } catch (IOException ex) { }
    }
}
