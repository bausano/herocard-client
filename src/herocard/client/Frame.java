package herocard.client;

import javax.swing.*;
import javax.swing.border.*;

public class Frame extends JFrame {    
    public Frame(int width, int height) {
        super("HeroCard");
        
        this.setVisible(true);
        
        // Setting window size in px.
        this.setSize(width, height);
        
        // Booting the close button.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centering the window.
        this.setLocationRelativeTo(null);
    }
    
    protected void addMargin(JComponent component, int[] size) {
        Border border = component.getBorder();
        
        Border margin = new EmptyBorder(size[0], size[1], size[2], size[3]);
        
        component.setBorder(new CompoundBorder(border, margin));
    }
}
