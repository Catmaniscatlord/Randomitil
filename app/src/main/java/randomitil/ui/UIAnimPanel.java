// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;

// Class Declaration
public class UIAnimPanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    // Class Objects
    private TilingsDrawer drawer;
    private TilingsAnimator animator;

    /// Constructor ///
    public UIAnimPanel(TilingsDrawer drawer, TilingsAnimator animator, Border border) {
        // Store Drawer
        this.setDrawer(drawer);

        // Create Animator
        this.setAnimator(animator);

        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Animation", TitledBorder.LEFT, TitledBorder.TOP));

        // Setup GridLayout
        this.setLayout(new GridLayout(3, 2, 5, 5));

        // Button Instantiation
        JButton playButton = new JButton("Play/Pause");
        JToggleButton animateToggle = new JToggleButton("Animate?");
        JSlider animSpeedSlider = new JSlider(0, 10, 1);
        JSlider sizeSlider = new JSlider(0, 10, 1);
        

        // Adding Listeners to Buttons
        playButton.addActionListener(e -> 
            animator.setAutoAnimate(!animator.isAutoAnimate())
        );

        animateToggle.addActionListener(e -> 
            drawer.setAnimate(animateToggle.isSelected())
        );

        animSpeedSlider.addChangeListener(e -> 
            drawer.setAnimSpeed(animSpeedSlider.getValue())
        );

        sizeSlider.addChangeListener(e -> 
            animator.setFinalSize(sizeSlider.getValue())
        );

        // Add Buttons to Panel
        this.add(playButton);
        this.add(animateToggle);
        this.add(animSpeedSlider);
        this.add(sizeSlider);
    }

    /// Setter Methods ///--------------------------
    public void setDrawer(TilingsDrawer drawer) {
        this.drawer = drawer;
    }

    public void setAnimator(TilingsAnimator animator) {
        this.animator = animator;
    }

    /// Getter Methods ///--------------------------
    public TilingsDrawer getDrawer() {
        return drawer;
    }

    public TilingsAnimator getAnimator() {
        return animator;
    }
}