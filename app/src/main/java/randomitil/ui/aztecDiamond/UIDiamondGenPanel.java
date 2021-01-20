// Package Declaration
package randomitil.ui.aztecDiamond;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.aztecDiamond.*;

// Class Declaration
public class UIDiamondGenPanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;
    
    // Class Objects
    DiamondDrawer drawer;
    
    private DiamondAnimator animator;

    /// Constructor ///
    public UIDiamondGenPanel(DiamondDrawer drawer, Border border) {
        // Store Drawer
        this.drawer = drawer;

        // Create Animator
        animator = new DiamondAnimator(drawer);

        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Generation", TitledBorder.LEFT, TitledBorder.TOP));
        
        // Setup GridLayout
        this.setLayout(new GridLayout(1, 2, 5, 5));

        // Button Instantiation
        JButton iterateButton = new JButton("Iterate"); 
        JButton resetButton = new JButton("Reset"); 

        
        // Adding Listeners to Buttons
        iterateButton.addActionListener(e -> {
            if (!drawer.getAnimate()) {
                animator.iterateTiling();
            }
        });

        resetButton.addActionListener(e -> {
            if (!drawer.getAnimate()) {
                animator.setupTiling();
            }
        });

        // Add Buttons to Panel
        this.add(iterateButton);
        this.add(resetButton);
    }
}