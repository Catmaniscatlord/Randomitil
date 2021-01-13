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
    JPanel uiColorChangePanel;
    
    /// Constructor ///
    public DominoUI(DominoDrawer drawer) {
        // Setup Background
        setBackground(new Color (240, 240, 240));
        setPreferredSize(new Dimension(300, 300));

        // Setup Panels
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        uiGenPanel = new JPanel();
        uiColorPanel = new JPanel();
        uiColorChangePanel = new JPanel();

        // Setup Border
        uiBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        // setup variables
        this.drawer = drawer;

        // Setup Buttons
        setupGenOptions();
        setupColorOptions();
        setupColorChangeOptions();

        // Add Panels
        this.add(uiGenPanel);
        this.add(uiColorPanel);
        this.add(uiColorChangePanel);
    }

/// Adding Generation Options ///
public void setupGenOptions() {
    // Setup Border
    uiGenPanel.setBorder(BorderFactory.createTitledBorder(uiBorder, "Generation", TitledBorder.LEFT, TitledBorder.TOP));

    // Setup FlowLayout
    uiGenPanel.setLayout(new GridLayout(1, 1, 5 ,5));
    
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
    uiColorPanel.setBorder(BorderFactory.createTitledBorder(uiBorder, "Colors", TitledBorder.LEFT, TitledBorder.TOP));

    // Setup Layout
    uiColorPanel.setLayout(new GridLayout(3, 2, 5 ,5));
    
    // Button Instantiation
    JButton backColorButton = new JButton("Background Color");
    JButton outlineColorButton = new JButton("Outline Color");
    JButton uColorButton = new JButton("Up Color");
    JButton rColorButton = new JButton("Right Color");
    JButton dColorButton = new JButton("Down Color");
    JButton lColorButton = new JButton("Left Color");

    // Button Listeners
    ActionListener outlineColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Outline Color", drawer.getOutlineColor());

            // Change Variables
            drawer.setOutlineColor(c);
        }
    };

    ActionListener backColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Background Color", drawer.getBackground());

            // Change Variables
            drawer.setBackground(c);
        }
    };

    ActionListener uColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Up Color", Color.BLUE);

            // Change Variables
            drawer.setDirColor(c, 0);
        }
    };

    ActionListener rColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Right Color", Color.RED);

            // Change Variables
            drawer.setDirColor(c, 1);
        }
    };

    ActionListener dColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Down Color", Color.YELLOW);

            // Change Variables
            drawer.setDirColor(c, 2);
        }
    };

    ActionListener lColorButtonListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            // Get input value
            Color c = JColorChooser.showDialog(uiColorPanel, "Left Color", Color.GREEN);

            // Change Variables
            drawer.setDirColor(c, 3);
        }
    };

    // Adding Listeners to Buttons
    outlineColorButton.addActionListener(outlineColorButtonListener);
    backColorButton.addActionListener(backColorButtonListener);
    uColorButton.addActionListener(uColorButtonListener);
    rColorButton.addActionListener(rColorButtonListener);
    dColorButton.addActionListener(dColorButtonListener);
    lColorButton.addActionListener(lColorButtonListener);

    // Add Buttons to Panel
    uiColorPanel.add(outlineColorButton);
    uiColorPanel.add(backColorButton);
    uiColorPanel.add(uColorButton);
    uiColorPanel.add(rColorButton);
    uiColorPanel.add(dColorButton);
    uiColorPanel.add(lColorButton);
}

    /// Adding Color Change Options ///
    public void setupColorChangeOptions() {
        // Setup Border
        uiColorChangePanel.setBorder(BorderFactory.createTitledBorder(uiBorder, "Color Changing", TitledBorder.LEFT, TitledBorder.TOP));

        // Setup FlowLayout
        uiColorChangePanel.setLayout(new GridLayout(2, 2, 5 ,5));
        
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
                clockwiseToggle.setEnabled(!selected);

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
        uiColorChangePanel.add(colorChangeToggle);
        uiColorChangePanel.add(clockwiseToggle);
        uiColorChangePanel.add(colorStopButton);
    }
}