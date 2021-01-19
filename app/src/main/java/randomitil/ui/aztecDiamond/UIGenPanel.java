// Package Declaration
package randomitil.ui.aztecDiamond;

// Imports
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;
import randomitil.tilings.aztecDiamond.DiamondIteration;
import randomitil.tilings.aztecDiamond.DiamondTilings;

// Class Declaration
public class UIGenPanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;
    
    // Class Objects
    DiamondDrawer drawer;
    
    private DiamondTilings diamondTiling;
    private DiamondIteration diamondIterator;

    /// Constructor ///
    public UIGenPanel(DiamondDrawer drawer, Border border) {

        this.drawer = drawer;

        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Generation", TitledBorder.LEFT, TitledBorder.TOP));
        
        // Setup GridLayout
        this.setLayout(new GridLayout(1, 2, 5, 5));

        diamondSetup();

        // Button Instantiation
        JToggleButton animateToggle = new JToggleButton("Animation", false);
        JButton iterateDiamond = new JButton("iterate"); 

        
        // Adding Listeners to Buttons
        animateToggle.addActionListener(e -> {
            drawer.setAnimate(animateToggle.isSelected());
        });

        iterateDiamond.addActionListener(e -> {
            iterateDiamond();
        });

        // Add Buttons to Panel
        this.add(animateToggle);
        this.add(iterateDiamond);
    }

    private void diamondSetup() {
        if(diamondTiling != null) {
            diamondTiling = new DiamondTilings();
        }
        else {
            diamondTiling = new DiamondTilings();
        }
        diamondIterator = new DiamondIteration(diamondTiling);
        
        diamondIterator.setAnimationMode(false);
        diamondIterator.setTilingBias(0.5);

        diamondIterator.fillBlankSpaces();

        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator, diamondIterator.getTiling().getSize());
    }

    private void iterateDiamond() {
        diamondIterator.iterateTiles();

        drawer.updateTiling((DiamondTilings) diamondIterator.getTiling(), diamondIterator, diamondIterator.getTiling().getSize());
    }
}