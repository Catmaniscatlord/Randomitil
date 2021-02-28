// Package Declaration
package randomitil.ui.aztecDiamond;

// Imports
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;
import randomitil.ui.UIColorChooser;

// Class Declaration
public class UIDiamondColorPanel extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;
    
    // Class Objects
    TilingsDrawer drawer;
    UIDiamondColorPanel panel;

    JColorChooser colorChooser;
    JDialog colorDialog;
    
    // CancelListener Class
    private class CancelListener implements ActionListener, Serializable {
        // Serialization
        private static final long serialVersionUID = 3337828037218852291L;

        // Class Variables
        private JColorChooser chooser;
        private Color initColor;

        public CancelListener(JColorChooser chooser) {
            super();

            this.chooser = chooser;
        }

        /// Reset Initial Color
        public void setInitColor(Color color) {
            this.initColor = color;
        }

        /// Action Performed ///
        public void actionPerformed(ActionEvent e) {
            chooser.setColor(this.initColor);
        }
    }

    CancelListener cancelListener;

    /// Constructor ///
    public UIDiamondColorPanel(TilingsDrawer drawer, Border border) {
        // Setup Border
        this.setBorder(BorderFactory.createTitledBorder(border, "Colors", TitledBorder.LEFT, TitledBorder.TOP));
        
        // Setup GridLayout
        this.setLayout(new GridLayout(3, 2, 5, 5));

        // Button Instantiation
        JButton backColorButton = new JButton("Background Color");
        JButton outlineColorButton = new JButton("Outline Color");
        JButton uColorButton = new JButton("Up Color");
        JButton rColorButton = new JButton("Right Color");
        JButton dColorButton = new JButton("Down Color");
        JButton lColorButton = new JButton("Left Color");

        // Setup self
        this.panel = this;

        // Setup Color Choice dialog
        this.colorChooser = new UIColorChooser();

        // Setup Cancel Listener
        this.cancelListener = new CancelListener(this.colorChooser);

        // Add Button Listeners
        outlineColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getOutlineColor());

		    // Change Variables
		    if (c != null) {
		        drawer.setOutlineColor(c);
		    }
		});

        backColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getBackground());

		    // Change Variables
		    if (c != null) {
		        drawer.setBackground(c);
		    }
		});

        uColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getDirColor(0));

		    // Change Variables
		    if (c != null) {
		        drawer.setDirColor(c, 0);
		    }
		});

        rColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getDirColor(1));

		    // Change Variables
		    if (c != null) {
		        drawer.setDirColor(c, 1);
		    }
		});

        dColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getDirColor(2));

		    // Change Variables
		    if (c != null) {
		        drawer.setDirColor(c, 2);
		    }
		});

        lColorButton.addActionListener(e -> {
		    // Get input value
		    Color c = panel.createColorDialog(drawer.getDirColor(3));

		    // Change Variables
		    if (c != null) {
		        drawer.setDirColor(c, 3);
		    }
		});

        // Add Buttons to Panel
        this.add(outlineColorButton);
        this.add(backColorButton);
        this.add(uColorButton);
        this.add(rColorButton);
        this.add(dColorButton);
        this.add(lColorButton);

        // Setup Size
        setSize();
    }

    /// Setting Sizes Method ///
    private void setSize() {
        this.setMaximumSize(new Dimension(525, 140));
        this.setPreferredSize(new Dimension(420, 112));
        this.setMinimumSize(new Dimension(210, 56));
    }

    /// Create Color Dialog Method ///
    public Color createColorDialog(Color initColor) {
        // Create Dialog
        this.cancelListener.setInitColor(initColor);
        this.colorChooser.setColor(initColor);
        this.colorDialog = JColorChooser.createDialog(panel, "Choose Outline Color", true, colorChooser, null, cancelListener);

        // Show Dialog
        this.colorDialog.pack();
        this.colorDialog.setVisible(true);

        // return
        return this.colorChooser.getColor();
    }
}