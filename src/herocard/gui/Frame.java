package herocard.gui;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Abstract GUI frame with default graphics.
 * 
 * @author michael
 */
public abstract class Frame extends JFrame {
    /**
     * __construct
     * @param width Width of frame in pixels.
     * @param height Height of frame in pixels.
     */
    public Frame(int width, int height) {
        super("HeroCard");
        
        // Sets default background color to gray.
        setBackground(Color.LIGHT_GRAY);
                
        // Setting window size in px.
        setSize(width, height);
        
        // Booting the close button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centering the window.
        setLocationRelativeTo(null);
    }
    
    /**
     * Adds a margin to given window component. With buttons this function
     * behaves like a padding.
     * 
     * @param component Component of the window.
     * @param size Array of int as follows [top, left, bottom, right].
     */
    protected void addMargin(JComponent component, int[] size) {
        Border border = component.getBorder();
        
        Border margin = new EmptyBorder(size[0], size[1], size[2], size[3]);
        
        component.setBorder(new CompoundBorder(border, margin));
    }
}
