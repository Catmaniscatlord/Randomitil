// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.tilings.aztecDiamond.DiamondIteration;
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
        JToggleButton spinToggle = new JToggleButton("Party Time");
        JSlider animSpeedSlider = new JSlider(0, 10, 1);
        JFormattedTextField numIterateField = new JFormattedTextField();

        numIterateField.setValue(Integer.valueOf(1));
        

        // Adding Listeners to Buttons
        playButton.addActionListener(e -> {
            animator.setAutoAnimate(!animator.isAutoAnimate());
        });

        spinToggle.addActionListener(e -> {
            drawer.setSpin(spinToggle.isSelected());
        });

        animSpeedSlider.addChangeListener(e -> 
            drawer.setAnimSpeed(animSpeedSlider.getValue())
        );

        numIterateField.addActionListener(e -> {
            // Correct inputted number
            if ((int) numIterateField.getValue() <= 0) {
                numIterateField.setValue(Integer.valueOf(1));
            }

            // Set Number of Iterations
            animator.setNumIterate((int) numIterateField.getValue());
        });

        // Setup Labels
        JLabel animSpeedLabel = new JLabel("Animation Speed:", 4);
        JLabel numIterateLabel = new JLabel("Number of Iterations:", 4);

        animSpeedLabel.setLabelFor(animSpeedSlider);
        numIterateLabel.setLabelFor(numIterateField);

        // Add Buttons & labels to Panel
        this.add(playButton);

        this.add(spinToggle);

        this.add(animSpeedLabel);
        this.add(animSpeedSlider);

        this.add(numIterateLabel);
        this.add(numIterateField);

        // Set Size
        setSize();
    }

    /// Setting Sizes Method ///
    private void setSize() {
        this.setMaximumSize(new Dimension(525, 105));
        this.setPreferredSize(new Dimension(420, 84));
        this.setMinimumSize(new Dimension(210, 42));
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