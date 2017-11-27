package herocard.events;

import java.util.ArrayList;

/**
 *
 * @author michael
 */
public interface Triggable {
    public String method();
    
    public ArrayList<Object> getListeners();
    
    public void addListener(Object listener);
}
