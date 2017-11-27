package herocard.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author michael
 */
public class Event {
    /**
     * Event register.
     */
    private static final HashMap<String, Triggable> events = new HashMap() {{
        put("connected", new Connected());
        put("disconnected", new Disconnected());
    }};
    
    /**
     * Add a listener object for an event.
     * 
     * @param listener
     * @param e 
     */
    public static void addListenerToEvent(Object listener, String e) {
        if (! events.containsKey(e)) {
            return;
        }
        
        Triggable event = events.get(e);
        
        event.addListener(listener);
    }
    
    /**
     * Dispatches given event name to event listeners.
     * 
     * @param e 
     */
    public static void dispatch(String e) {
        if (! events.containsKey(e)) {
            return;
        }
        
        Triggable event = events.get(e);
        
        String method = event.method();
        
        ArrayList<Object> listeners = event.getListeners();
        
        listeners.forEach((listener) -> {
            try {
                triggerListenerMethod(listener, method);
            } catch(Exception ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
    
    /**
     * Calls listening method on listener.
     * 
     * @param listener
     * @param method
     * @throws Exception 
     */
    private static void triggerListenerMethod(Object listener, String method) throws Exception {
        Method trigger = listener.getClass().getDeclaredMethod(method);
        
        trigger.invoke(listener);
    }
}
