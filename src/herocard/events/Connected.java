package herocard.events;

import herocard.gui.Menu;

/**
 * Connected event is triggered every time a connection with server is established.
 * 
 * @author michael
 */
public class Connected extends Event {
    /**
     * List of objects that listen to this event.
     */
    public Connected() {
        listeners.add(Menu.getInstance());
    }
    
    /**
     * Listener should implement following method.
     * 
     * @return Annotation of method that shall be called on listener object.
     */
    @Override
    public String method() {
        return "onConnectionEstablished";
    }
}
