package herocard.gui;

import herocard.client.Client;

/**
 * A GUI client for HeroCard game.
 * 
 * @author michael
 */
public class Main {
    public static void main(String[] args) {
        // Boots GUI.
        Menu.getInstance();
        
        Client.connect("localhost", 5551);
    }
}
