package herocard.events;

import herocard.gui.Menu;
import herocard.listeners.ConnectedListener;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class Connected implements Event {
    private static final Event instance = new Connected();

    private final ArrayList<ConnectedListener> listeners = new ArrayList() {{
        add(Menu.getInstance());
    }};
    
    @Override
    public void trigger() {
        int size = listeners.size();
        
        for(int x = 0; x < size; x++) {
            listeners.get(x).onConnectionEstablished();
        }
    }
    
    public void addListener(ConnectedListener listener) {
        listeners.add(listener);
    }

    public static Event event() {
        return instance;
    }
}
