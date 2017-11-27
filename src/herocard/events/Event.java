package herocard.events;

import java.util.ArrayList;

/**
 * Each event must extend Event class.
 * 
 * @author michael
 */
abstract class Event {
    /**
     * List of an event listeners.
     */
    protected ArrayList<Object> listeners = new ArrayList();
    
    /**
     * Listener should implement following method.
     * 
     * @return Annotation of method that shall be called on listener object.
     */
    public abstract String method();
    
    /**
     * @return List of an event listeners.
     */
    public ArrayList<Object> getListeners() {
        return listeners;
    }
    
    /**
     * Adds an object as a listener.
     * 
     * @param listener 
     */
    public void addListener(Object listener) {
        listeners.add(listener);
    }
}
