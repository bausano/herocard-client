package herocard.events;

import herocard.client.Client;
import herocard.gui.Menu;

/**
 * Disconnected event is triggered once a connection with server is lost.
 *
 * @author michael
 */
public class Disconnected extends Event {
    /**
     * List of objects that listen to this event.
     */
    public Disconnected() {
        listeners.add(Menu.getInstance());
        listeners.add(new Client());
    }
    
    /**
     * Listener should implement following method.
     * 
     * @return Annotation of method that shall be called on listener object.
     */
    @Override
    public String method() {
        return "onConnectionLost";
    }
}
