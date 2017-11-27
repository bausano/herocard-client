package herocard.client;

import herocard.events.Emitor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Request is asynchronously sent to the server. 
 *
 * @author michael
 */
public class Request {
    /**
     * Body of a request.
     */
    public String body;
    
    /**
     * Priority of request, preferably in values 1 - 10.
     */
    public Integer priority = 5;
    
    /**
     * Lambda that's called with response body as argument. 
     */
    Callback resolve;
    
    /**
     * Protocol connection.
     */
    public final Connection conn;
    
    /**
     * 
     * @param conn 
     */
    public Request(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Starts the sending process.
     * @throws java.io.IOException
     */
    public void execute() throws IOException {
        if (! conn.isConnected()) {
            throw new IOException();
        }

        send();

        String response = read();

        resolve.call(response);
    }
    
    /**
     * Sends request body on server.
     * 
     * @throws IOException 
     */
    private void send() throws IOException {
        DataOutputStream out = new DataOutputStream(conn.out());
        
        out.writeBytes(body);
    }
    
    /**
     * Reads response body from server.
     * 
     * @return Response body.
     * @throws IOException 
     */
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
    
    /**
     * Returns priority for comparing in PriorityQueue.
     * 
     * @return Priority integer.
     */
    public Integer getPriority() {
        return priority;
    }
}
