// Package Declaration
package randomitil.animation;

// Imports
import java.awt.EventQueue;
import javax.swing.JFrame;

// Class Declaration
public class DominoInterface extends JFrame {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    /// Constructor ///
    public DominoInterface() {
        // Initialize UI
        initUI();
    }
    
    /// Initialization ///
    private void initUI() {
        // Add Domino Drawing Region
        add(new DominoDrawer());

        // Setup
        setResizable(true);
        pack();
        setTitle("Random Domino Tiling");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    /// Setup Method ///
    public static void setup() {
        EventQueue.invokeLater(() -> {
            // Create Self
            JFrame ex = new DominoInterface();

            // Set Visibility
            ex.setVisible(true);
        });
    }
}