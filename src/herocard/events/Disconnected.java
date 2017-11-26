package herocard.events;

import herocard.gui.Menu;
import herocard.listeners.DisconnectedListener;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class Disconnected implements Event {
    private static final Disconnected instance = new Disconnected();

    private final ArrayList<DisconnectedListener> listeners = new ArrayList() {{
        add(Menu.getInstance());
    }};
    
    @Override
    public void trigger() {
        int size = listeners.size();
        
        for(int x = 0; x < size; x++) {
            listeners.get(x).onConnectionLost();
        }
    }
    
    public void addListener(DisconnectedListener listener) {
        listeners.add(listener);
    }

    public static Disconnected event() {
        return instance;
    }
}
