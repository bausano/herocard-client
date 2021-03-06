package herocard.client;

/**
 * Message helps to compose the request body.
 *
 * @author michael
 */
public class Message {
    /**
     * Each request body has to end with following delimiter.
     * Is defined server side.
     */
    public static final String DELIMITER = "$";
    
    /**
     * A command that gets routed server side.
     */
    private final String command;
    
    /**
     * Optional arguments.
     */
    private String arguments;
    
    
    /**
     * Request instance.
     */
    public Request request;
    
    /**
     * @param request New request instance.
     * @param command 
     */
    public Message(Request request, String command) {
        this.request = request;
        
        this.command = command;
    }
    
    /**
     * Optionally request body can have arguments.
     * 
     * @param args
     * @return 
     */
    public Message args(String... args) {
        // Follows a server side defined standard.
        arguments = String.join(";", args);
        
        return this;
    }
    
    /**
     * Optionally request can have different priority.
     * By default, priority is 5.
     * 
     * @param priority Priority on scale 1 - 10.
     * @return 
     */
    public Message priority(Integer priority) {
        request.priority = priority;
        
        return this;
    }
    
    /**
     * Executes query.
     * 
     * @param cb Lambda with one string argument - response body.
     */
    public void send(Callback cb) {
        request.body = command;
        
        if (arguments != null) {
            request.body += ";" + arguments;
        }
        
        request.body += Message.DELIMITER;
        
        request.resolve = cb;
        
        request.conn.spawn(request);
    }
}
