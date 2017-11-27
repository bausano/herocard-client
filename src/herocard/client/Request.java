package herocard.client;

import herocard.events.Event;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Request is asynchronously sent to the server. 
 *
 * @author michael
 */
public class Request implements Runnable {
    /**
     * Body of a request.
     */
    public String body;
    
    /**
     * Lambda that's called with response body as argument.
     */
    private Callback resolve;
    
    /**
     * Protocol connection.
     */
    private final Connection conn;
    
    /**
     * 
     * @param conn 
     */
    public Request(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Starts the sending process on a new thread.
     * 
     * @param cb
     * @return 
     */
    public Thread spawn(Callback cb) {
        resolve = cb;

        Thread t = new Thread(this);

        t.start();

        return t;
    }

    /**
     * Sends the request and waits for a response.
     */
    @Override
    public void run() {
        try {
            if (! conn.isConnected()) {
                throw new IOException();
            }
            
            send();
            
            String response = read();
            
            resolve.call(response);
        } catch (IOException ex) {
            Event.dispatch("disconnected");
        }
    }
    
    private void send() throws IOException {
        DataOutputStream out = new DataOutputStream(conn.out());
        
        out.writeBytes(body);
    }
    
    private String read() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.in()));
        
        String line;

        String acc = "";

        // Read the input stream until it is ended with delimiter.
        while(! (line = reader.readLine()).endsWith(Message.DELIMITER)) {
            acc += line;
        }
        
        return acc;
    }
}
