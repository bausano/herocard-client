package herocard.client.events;

import java.awt.event.*;
import herocard.client.*;

public class NewGameListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        Menu menu = Menu.getInstance();
        
        menu.dispose();
        
        // TODO: Open window "Wait for another player".
        Game game = Game.getInstance();
    }    
}
