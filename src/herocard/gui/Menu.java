package herocard.gui;

import herocard.gui.events.NewGameListener;
import herocard.gui.events.CloseWindowListener;
import herocard.gui.events.SearchGameListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Initial GUI Menu.
 * 
 * @author michael
 */
public class Menu extends Frame {
    /**
     * Singelton instance.
     */
    public static Menu instance = new Menu();
    
    /**
     * Heading text.
     */
    public final JLabel heading = new JLabel("HeroCard");
    
    /**
     * New game button opens a new window game window.
     */
    private final JButton newGame = new JButton("New game");
    
    /**
     * Connect to game button opens a new search game window.
     */
    private final JButton connectToGame = new JButton("Connect to game");
    
    /**
     * Exit button disposes window.
     */
    private final JButton closeWindow = new JButton("Exit");
    
    /**
     * __constructor
     */
    private Menu() {
        // Inits a new frame with given size.
        super(400, 600);
        
        // Sets layout and background color.
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
   
        // Prints the header.
        header();
        
        /**
         * Displays action buttons with appropriate listener.
         */
        prepareButton(this.newGame, new NewGameListener());
        
        prepareButton(this.connectToGame, new SearchGameListener());
        
        prepareButton(this.closeWindow, new CloseWindowListener(this));
        
        // Displays the window.
        setVisible(true);
    }
    
    /**
     * Positions a styled header with margin.
     */
    public final void header() {
        heading.setFont(new Font("Serif", Font.BOLD, 40));
        
        heading.setForeground(Color.DARK_GRAY);
        
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adds a margin around layout.
        addMargin(heading, new int[]{20, 0, 100, 0});

        add(heading);
    }
    
    /**
     * Prepares a button and displays it.
     * @param button JFrame button to display.
     * @param listener Listener to call on button click.
     */
    public final void prepareButton(JButton button, ActionListener listener) {
        // Aligns button to the center of the frame.
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adds a margin inside the button so that the button gets bigger.
        addMargin(button, new int[]{5, 5, 5, 5});
        
        // Attaches listener to the button.
        button.addActionListener(listener);
        
        // Displays button.
        add(button);
        
        // Puts a margin after the button.
        add(Box.createRigidArea(new Dimension(0, 50)));
    }
    
    /**
     * Returns a singelton instance.
     * 
     * @return Menu JFrame.
     */
    public static Menu getInstance() {
      return instance;
   }
}
