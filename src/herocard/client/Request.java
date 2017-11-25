package herocard.client;

import herocard.exceptions.ExceptionHandler;
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
            send(body);
            
            String response = read();
            
            resolve.call(response);
        } catch (IOException ex) {
            ExceptionHandler._catch(ex);
        }
    }
    
    private void send(String body) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.out());
        
        out.writeBytes(this.body);
    }
    
    private String read() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.in()));
        
              String line;

        String acc = "";

        while(! (line = reader.readLine()).endsWith(Message.DELIMITER)) {
            acc += line;
        }
        
        return acc;
    }
}
