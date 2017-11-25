package herocard.client;

/**
 *
 * @author michael
 */
public interface Callback {
    /**
     * 
     * @param response Response body from server.
     */
    void call(String response);
}
