// Package Declaration
package randomitil;

// Imports
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.*;

import randomitil.ui.*;
import randomitil.animation.*;

// Class Declaration
public class DominoInterface extends JFrame {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Globals
    private DominoDrawer drawer = null;
    private DominoUI ui = null;
    private JPanel mainPanel = null;

    /// Constructor ///
    public DominoInterface(DominoDrawer drawer, DominoUI ui) {
        // Initialize GUI Components
        this.drawer = drawer;
        this.ui = ui;
        initUI();
    }
    
    /// Initialization ///
    private void initUI() {
        // Create main panel
        mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(this.drawer);
        mainPanel.add(this.ui);

        // Add Main Panel
        add(this.mainPanel);

        // Setup
        setResizable(true);
        pack();
        setTitle("Random Domino Tiling");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /// Setup Method ///
    public static void setup(DominoDrawer drawer, DominoUI ui) {
        EventQueue.invokeLater(() -> {
            // Create Self
            JFrame ex = new DominoInterface(drawer, ui);

            // Set Visibility
            ex.setVisible(true);
        });
    }
}