package herocard.events;

import herocard.gui.Menu;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class Connected implements Triggable {
    private final ArrayList<Object> listeners = new ArrayList() {{
        add(Menu.getInstance());
    }};
    
    @Override
    public String method() {
        return "onConnectionEstablished";
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
