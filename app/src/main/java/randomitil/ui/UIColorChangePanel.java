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
        this.setLayout(new GridLayout(3, 2, 5, 5));

        // Button Instantiation
        JToggleButton colorChangeToggle = new JToggleButton("Color Rotation", false);
        JButton colorStopButton = new JButton("Reset Rotation");
        JSlider colorSpeedSlider = new JSlider(0, 10, 1);
        JToggleButton clockwiseToggle = new JToggleButton("Clockwise", true);
        

        // Add Button Listeners
        colorChangeToggle.addActionListener(e -> {
		    // Get input value
		    boolean selected = colorChangeToggle.isSelected();

		    // Change Variables
		    drawer.setColorChange(selected);
		    clockwiseToggle.setEnabled(!selected);
		});

        colorStopButton.addActionListener(e -> {
		    // Stop Color Changing
            colorChangeToggle.setSelected(false);
            clockwiseToggle.setEnabled(true);
		    drawer.stopColorChange();
		});

        colorSpeedSlider.addChangeListener(e -> 
            drawer.setColorSpeed(colorSpeedSlider.getValue() / 5.0)
        );

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

        // Setup Labels
        JLabel colorSpeedLabel = new JLabel("Color Rotation Speed:", 4);

        colorSpeedLabel.setLabelFor(colorSpeedSlider);

        // Add Buttons & Labels to Panel
        this.add(colorChangeToggle);

        this.add(colorStopButton);

        this.add(colorSpeedLabel);
        this.add(colorSpeedSlider);

        this.add(clockwiseToggle);

        // Set Size
        setSize();
    }

    /// Setting Sizes Method ///
    private void setSize() {
        this.setMaximumSize(new Dimension(525, 140));
        this.setPreferredSize(new Dimension(420, 112));
        this.setMinimumSize(new Dimension(210, 56));
    }
}