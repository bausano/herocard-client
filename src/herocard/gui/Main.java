package herocard.gui;

import herocard.client.Callback;
import herocard.client.Client;
import herocard.exceptions.ExceptionHandler;
import java.io.IOException;

/**
 * A GUI client for HeroCard game.
 * 
 * @author michael
 */
public class Main {
    public static void main(String[] args) {
        try {
            Client.connect("localhost", 5551);
        } catch (IOException ex) {
            ExceptionHandler._catch(ex);
        }
        
        // Boots GUI.
        //Menu.getInstance();

        Callback cb = (String response) -> {
            System.out.print(response);
        };

        Client.message("connect").args("michael", "156").send(cb);
    }
}
