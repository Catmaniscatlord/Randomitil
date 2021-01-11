// Package Declaration
package randomitil.animation;

// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import randomitil.*;
import java.awt.FlowLayout;
import java.lang.Runnable;
import java.lang.Math;

// Class Declaration
public class DominoUI extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Class Objects
    DominoDrawer drawer;
    Border uiBorder;
    JPanel uiGenPanel;
    JPanel uiColorPanel;
    
    /// Constructor ///
    public DominoUI(DominoDrawer drawer) {
        // Setup Background
        setBackground(new Color (235, 235, 235));
        setPreferredSize(new Dimension(300, 300));

        // Setup Panels
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        uiGenPanel = new JPanel();
        uiColorPanel = new JPanel();

        // Setup Border
        uiBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        // setup variables
        this.drawer = drawer;

        // Setup Buttons
        setupGenOptions();
        setupColorOptions();

        // Add Panels
        this.add(uiGenPanel);
        this.add(uiColorPanel);
    }

/// Adding Generation Options ///
public void setupGenOptions() {
    // Setup Border
    uiGenPanel.setBorder(BorderFactory.createTitledBorder(uiBorder, "Generation", TitledBorder.LEFT, TitledBorder.TOP));

    // Setup FlowLayout
    uiGenPanel.setLayout(new FlowLayout());
    
    // Button Instantiation
    JToggleButton animateToggle = new JToggleButton("Animation", false);

    // Button Listeners
    ActionListener animateToggleListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            boolean selected = animateToggle.isSelected();

            // Change Variables
            drawer.setAnimate(selected);

            // Inform of Change
            System.out.println("Animate: " + selected + "\n");
        }
    };

    // Adding Listeners to Buttons
    animateToggle.addActionListener(animateToggleListener);

    // Add Buttons to Panel
    uiGenPanel.add(animateToggle);
}

    /// Adding Color Options ///
    public void setupColorOptions() {
        // Setup Border
        uiColorPanel.setBorder(BorderFactory.createTitledBorder(uiBorder, "Color Changing", TitledBorder.LEFT, TitledBorder.TOP));

        // Setup FlowLayout
        uiColorPanel.setLayout(new FlowLayout());
        
        // Button Instantiation
        JToggleButton colorChangeToggle = new JToggleButton("Color Rotation", false);
        JToggleButton clockwiseToggle = new JToggleButton("Clockwise", true);
        JButton colorStopButton = new JButton("Reset Coloring");

        // Button Listeners
        ActionListener colorChangeToggleListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // Get input value
                boolean selected = colorChangeToggle.isSelected();

                // Change Variables
                drawer.setColorChange(selected);

                // Inform of Change
                System.out.println("Color Changing: " + selected + "\n");
            }
        };

        ActionListener clockwiseToggleListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // Get input value
                boolean selected = clockwiseToggle.isSelected();

                // Change Variables
                if (selected) {
                    drawer.setColorStep(-1);
                } else {
                    drawer.setColorStep(1);
                }

                // Inform of Change
                System.out.println("Color Changing: " + selected + "\n");
            }
        };

        ActionListener colorStopButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // Stop Color Changing
                colorChangeToggle.setSelected(false);
                drawer.stopColorChange();

                // Inform of Change
                System.out.println("Color Changing Stopped\n");
            }
        };

        // Adding Listeners to Buttons
        colorChangeToggle.addActionListener(colorChangeToggleListener);
        clockwiseToggle.addActionListener(clockwiseToggleListener);
        colorStopButton.addActionListener(colorStopButtonListener);

        // Add Buttons to Panel
        uiColorPanel.add(colorChangeToggle);
        uiColorPanel.add(clockwiseToggle);
        uiColorPanel.add(colorStopButton);
    }
}