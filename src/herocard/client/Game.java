package herocard.client;

import javax.swing.*;

public class Game extends Frame {
    public static Game instance = new Game();
    
    private Game() {
        super(900, 700);
                
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }
    
    public static Game getInstance() {
        return instance;
    }
}
