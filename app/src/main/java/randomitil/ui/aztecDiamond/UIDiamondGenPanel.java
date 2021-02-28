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
        this.setLayout(new GridLayout(3, 2, 5, 5));

        // Button Instantiation
        JButton iterateButton = new JButton("Iterate");
        JButton resetButton = new JButton("Reset");
        JFormattedTextField biasField = new JFormattedTextField();
        JFormattedTextField expRateField = new JFormattedTextField();
        
        biasField.setValue(Double.valueOf(0.5));
        expRateField.setValue(Integer.valueOf(1));

        // Adding Listeners to Buttons
        iterateButton.addActionListener(e -> {
            if (!drawer.isAnimate()) {
                animator.iterateTiling();
            }
        });

        resetButton.addActionListener(e -> 
            reset(biasField, expRateField)
        );

        biasField.addActionListener(e -> {
            double newBias = Math.min(Math.max(0, (double) biasField.getValue()), 1);
            animator.getIterator().setTilingBias(newBias);
            biasField.setValue(Double.valueOf(newBias));
        });

        expRateField.addActionListener(e -> {
            // Adjust Text Field Entry
            int temp = Math.max(1, convertNumOdd((int) expRateField.getValue()));
            DiamondIteration iterator = (DiamondIteration) animator.getIterator();

            // Change Expansion Rate
            if (temp > 1) {
                iterator.setExpansionRate(temp);
                expRateField.setValue(Integer.valueOf(temp));
            } else {
                expRateField.setValue(Integer.valueOf(1));
            }

            // Reset For Expansion Rate Change
            reset(biasField, expRateField);
        });

        // Setup Labels
        JLabel biasLabel = new JLabel(" Generation Bias:", 4);
        JLabel expRateLabel = new JLabel(" Expansion Rate:", 4);

        biasLabel.setLabelFor(biasField);
        expRateLabel.setLabelFor(expRateField);

        // Add Buttons & Labels to Panel
        this.add(iterateButton);

        this.add(resetButton);

        this.add(biasLabel);
        this.add(biasField);

        this.add(expRateLabel);
        this.add(expRateField);

        // Set Size
        setSize();
    }

    /// Reset Method ///
    private void reset(JFormattedTextField biasField, JFormattedTextField expRateField) {
        if (!drawer.isAnimate()) {
            DiamondIteration iterator = (DiamondIteration) animator.getIterator();
            int temp = Math.max(1, convertNumOdd((int) expRateField.getValue()));
            iterator.setExpansionRate(temp);

            if (temp == 1) {
                animator.setupTiling();
            } else {
                animator.sameRateSetup(Math.max(1, convertNumOdd((int) expRateField.getValue())));
            }

            drawer.resetPrevTiles();
            iterator.setTilingBias(Math.min(Math.max(0, (double) biasField.getValue()), 1));
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