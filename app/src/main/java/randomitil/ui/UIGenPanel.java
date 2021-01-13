// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;

// Class Declaration
public class UIGenPanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;
    
    // Class Objects
    DominoDrawer drawer;
    
    /// Constructor ///
    public UIGenPanel(DominoDrawer drawer, Border border) {
        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Generation", TitledBorder.LEFT, TitledBorder.TOP));
        
        // Setup GridLayout
        this.setLayout(new GridLayout(1, 1, 5, 5));

        // Button Instantiation
        JToggleButton animateToggle = new JToggleButton("Animation", false);

        // Button Listeners
        ActionListener animateToggleListener = actionEvent -> {
            // Get input value
            boolean selected = animateToggle.isSelected();

            // Change Variables
            drawer.setAnimate(selected);
        };

        // Adding Listeners to Buttons
        animateToggle.addActionListener(animateToggleListener);

        // Add Buttons to Panel
        this.add(animateToggle);
    }
}