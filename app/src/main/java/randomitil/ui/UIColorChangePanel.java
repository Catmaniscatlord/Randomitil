// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;

// Class Declaration
public class UIColorChangePanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;
    
    // Class Objects
    TilingsDrawer drawer;
    
    /// Constructor ///
    public UIColorChangePanel(TilingsDrawer drawer, Border border) {
        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Color Rotation", TitledBorder.LEFT, TitledBorder.TOP));
        
        // Setup GridLayout
        this.setLayout(new GridLayout(2, 2, 5, 5));

        // Button Instantiation
        JToggleButton colorChangeToggle = new JToggleButton("Color Rotation", false);
        JToggleButton clockwiseToggle = new JToggleButton("Clockwise", true);
        JButton colorStopButton = new JButton("Reset Rotation");

        // Add Button Listeners
        colorChangeToggle.addActionListener(e -> {
		    // Get input value
		    boolean selected = colorChangeToggle.isSelected();

		    // Change Variables
		    drawer.setColorChange(selected);
		    clockwiseToggle.setEnabled(!selected);
		});

        clockwiseToggle.addActionListener(e -> {
		    // Get input value
		    boolean selected = clockwiseToggle.isSelected();

		    // Change Variables
		    if (selected) {
		        drawer.setColorStep(-1);
		    } else {
		        drawer.setColorStep(1);
		    }
		});

        colorStopButton.addActionListener(e -> {
		    // Stop Color Changing
            colorChangeToggle.setSelected(false);
            clockwiseToggle.setEnabled(true);
		    drawer.stopColorChange();
		});

        // Add Buttons to Panel
        this.add(colorChangeToggle);
        this.add(clockwiseToggle);
        this.add(colorStopButton);
    }
}