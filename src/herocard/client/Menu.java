package herocard.client;

import javax.swing.*;
import java.awt.*;
import herocard.client.events.*;

public class Menu extends Frame {
    public static Menu instance = null;
    
    private final JLabel heading = new JLabel("HeroCard");
    
    private final JButton newGame = new JButton("New game"),
                          connectToGame = new JButton("Connect to game");
    
    public Menu() {
        super(400, 600);
        
        instance = this;
        
        // Sets layout and background color.
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        getContentPane().setBackground(Color.LIGHT_GRAY);
   
        // Inits the menu elements.
        header();
        
        newGameButton();
        
        // Creates space after button.
        add(Box.createRigidArea(new Dimension(0, 50)));
        
        connectButton();
    }
    
    /**
     * Adds a styled header with margin.
     */
    public final void header() {
        heading.setFont(new Font("Serif", Font.BOLD, 40));
        
        heading.setForeground(Color.DARK_GRAY);
        
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adds a margin around layout.
        addMargin(heading, new int[]{20, 0, 100, 0});

        add(heading);
    }
    
    public final void newGameButton() {
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adds a margin inside the button so that the button gets bigger.
        addMargin(newGame, new int[]{5, 5, 5, 5});
        
        add(newGame);
        
        newGame.addActionListener(new NewGame());
    }
    
    public final void connectButton() {
        connectToGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        addMargin(connectToGame, new int[]{5, 5, 5, 5});
        
        add(connectToGame);
    }
    
    public static Menu getInstance() {
      if(instance == null) {
         instance = new Menu();
      }
      return instance;
   }
}
