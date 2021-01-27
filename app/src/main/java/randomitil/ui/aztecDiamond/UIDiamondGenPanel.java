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
    private DiamondDrawer drawer;
    private DiamondAnimator animator;

    /// Constructor ///
    public UIDiamondGenPanel(DiamondDrawer drawer, DiamondAnimator animator, Border border) {
        // Store Drawer
        this.setDrawer(drawer);

        // Create Animator
        this.setAnimator(animator);

        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Generation", TitledBorder.LEFT, TitledBorder.TOP));

        // Setup GridLayout
        this.setLayout(new GridLayout(3, 2, 5, 5));

        // Button Instantiation
        JButton iterateButton = new JButton("Iterate");
        JSlider biasSlider = new JSlider(0, 100, 50);
        JSlider horzExpRateSlider = new JSlider(0, 10, 1);
        JSlider vertExpRateSlider = new JSlider(0, 10, 1);
        JButton resetButton = new JButton("Reset");

        // Adding Listeners to Buttons
        iterateButton.addActionListener(e -> {
            if (!drawer.isAnimate()) {
                animator.iterateTiling();
            }
        });

        // Adding Listeners to Buttons
        biasSlider.addChangeListener(e -> 
            animator.getIterator().setTilingBias(biasSlider.getValue() / 100.0)
        );

        resetButton.addActionListener(e -> {
            if (!drawer.isAnimate()) {
                animator.setupTiling();
            }
        });

        // Add Buttons to Panel
        this.add(iterateButton);
        this.add(biasSlider);
        this.add(horzExpRateSlider);
        this.add(vertExpRateSlider);
        this.add(resetButton);
    }

    /// Setter Methods ///--------------------------
    public void setDrawer(DiamondDrawer drawer) {
        this.drawer = drawer;
    }

    public void setAnimator(DiamondAnimator animator) {
        this.animator = animator;
    }

    /// Getter Methods ///--------------------------
    public DiamondDrawer getDrawer() {
        return drawer;
    }

    public DiamondAnimator getAnimator() {
        return animator;
    }
}