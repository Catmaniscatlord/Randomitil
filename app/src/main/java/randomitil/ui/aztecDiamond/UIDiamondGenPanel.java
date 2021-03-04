// Package Declaration
package randomitil.ui.aztecDiamond;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.tilings.aztecDiamond.DiamondIteration;
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
        this.setLayout(new GridLayout(4, 2, 5, 5));

        // Button Instantiation
        JButton iterateButton = new JButton("Iterate");
        JButton resetButton = new JButton("Reset");
        JFormattedTextField biasField = new JFormattedTextField();
        JFormattedTextField horzExpRateField = new JFormattedTextField();
        JFormattedTextField vertExpRateField = new JFormattedTextField();
        
        biasField.setValue(Double.valueOf(0.5));
        horzExpRateField.setValue(Integer.valueOf(1));
        vertExpRateField.setValue(Integer.valueOf(1));

        // Adding Listeners to Buttons
        iterateButton.addActionListener(e -> {
            if (!drawer.isAnimate()) {
                animator.iterateTiling();
            }
        });

        resetButton.addActionListener(e -> 
            // Reset Tiling
            reset(biasField, horzExpRateField, vertExpRateField)
        );

        biasField.addActionListener(e -> {
            // Change Bias
            double newBias = Math.min(Math.max(0, (double) biasField.getValue()), 1);
            animator.getIterator().setTilingBias(newBias);
            biasField.setValue(Double.valueOf(newBias));
        });

        horzExpRateField.addActionListener(e -> 
            // Reset For Horizontal Expansion Rate Change
            reset(biasField, horzExpRateField, vertExpRateField)
        );

        vertExpRateField.addActionListener(e -> 
            // Reset For Vertical Expansion Rate Change
            reset(biasField, horzExpRateField, vertExpRateField)
        );

        // Setup Labels
        JLabel biasLabel = new JLabel(" Generation Bias:", 4);
        JLabel horzExpRateLabel = new JLabel(" Horizontal Expansion Rate:", 4);
        JLabel vertExpRateLabel = new JLabel(" Vertical Expansion Rate:", 4);

        biasLabel.setLabelFor(biasField);
        horzExpRateLabel.setLabelFor(horzExpRateField);
        vertExpRateLabel.setLabelFor(vertExpRateField);

        // Add Buttons & Labels to Panel
        this.add(iterateButton);

        this.add(resetButton);

        this.add(biasLabel);
        this.add(biasField);

        this.add(horzExpRateLabel);
        this.add(horzExpRateField);

        this.add(vertExpRateLabel);
        this.add(vertExpRateField);

        // Set Size
        setSize();
    }

    /// Reset Method ///
    private void reset(JFormattedTextField biasField, JFormattedTextField horzExpRateField, JFormattedTextField vertExpRateField) {
        if (!drawer.isAnimate()) {
            // Get Iterator
            DiamondIteration iterator = (DiamondIteration) animator.getIterator();

            // Get Expansion Rates
            int horzExp = Math.max(1, convertNumOdd((int) horzExpRateField.getValue()));
            int vertExp = Math.max(1, convertNumOdd((int) vertExpRateField.getValue()));

            // Set Rate Fields
            horzExpRateField.setValue(Integer.valueOf(horzExp));
            vertExpRateField.setValue(Integer.valueOf(vertExp));

            // Get Bias
            double bias = Math.min(Math.max(0, (double) biasField.getValue()), 1);

            // Set Bias Field
            biasField.setValue(Double.valueOf(bias));

            // Setup for Expansion Rates
            if (horzExp == vertExp) {
                if (horzExp == 1) {
                    animator.setupTiling();
                } else {
                    animator.sameRateSetup(horzExp);
                }
            } else {
                animator.diffRateSetup(horzExp, vertExp);
            }

            // Last Setup Fix
            drawer.resetPrevTiles();
            iterator.setTilingBias(bias);
        }
    }

    /// Convert to odd number Method ///
    private int convertNumOdd(int num) {
        return num + (1 - num % 2);
    }

    /// Setting Sizes Method ///
    private void setSize() {
        this.setMaximumSize(new Dimension(525, 140));
        this.setPreferredSize(new Dimension(420, 112));
        this.setMinimumSize(new Dimension(210, 56));
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