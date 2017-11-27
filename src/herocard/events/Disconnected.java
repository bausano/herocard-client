package herocard.events;

import herocard.client.Client;
import herocard.gui.Menu;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class Disconnected implements Triggable {
   private final ArrayList<Object> listeners = new ArrayList() {{
        add(Menu.getInstance());
        add(new Client());
    }};
    
    @Override
    public String method() {
        return "onConnectionLost";
    }
    
    @Override
    public ArrayList<Object> getListeners() {
        return listeners;
    }
    
    @Override
    public void addListener(Object listener) {
        listeners.add(listener);
    }
}
