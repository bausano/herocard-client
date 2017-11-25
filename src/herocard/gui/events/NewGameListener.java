package herocard.gui.events;

import herocard.gui.Menu;
import herocard.gui.Game;
import java.awt.event.*;

public class NewGameListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {        
         Menu.getInstance().dispose();
        
        // TODO: Open window "Wait for another player".
       Game game = Game.getInstance();
    }    
}
