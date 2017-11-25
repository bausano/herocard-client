package herocard.gui.events;

import herocard.gui.Frame;
import java.awt.event.*;

/**
 * Closes a frame.
 * 
 * @author michael
 */
public class CloseWindowListener implements ActionListener {
    /**
     * Instance of window to close.
     */
    protected final Frame window;
    
    /**
     * __construct
     * 
     * @param window Instance of window to close.
     */
    public CloseWindowListener(Frame window) {
        this.window = window;
    }
    
    /**
     * @param event Event that triggered this listener.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Closes window.
        this.window.dispose();
    }
}
