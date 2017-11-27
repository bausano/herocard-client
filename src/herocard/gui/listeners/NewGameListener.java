package herocard.gui.listeners;

import herocard.client.Callback;
import herocard.client.Client;
import herocard.gui.Menu;
import herocard.gui.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        Callback cb = (String response) -> {
            Menu.getInstance().heading.setText(response);
        };

        Client.message("message1").send(cb);
        //Menu.getInstance().dispose();
        
        // TODO: Open window "Wait for another player".
       //Game game = Game.getInstance();
    }    
}
