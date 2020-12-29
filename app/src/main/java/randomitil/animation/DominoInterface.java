// Package Declaration
package randomitil.animation;

// Imports
import java.awt.EventQueue;
import javax.swing.JFrame;

// Class Declaration
public class DominoInterface extends JFrame {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Globals
    public DominoDrawer drawer = null;

    /// Constructor ///
    public DominoInterface(DominoDrawer _drawer) {
        // Initialize UI
        initUI(_drawer);
    }
    
    /// Initialization ///
    private void initUI(DominoDrawer _drawer) {
        // Add Domino Drawing Region
        this.drawer = _drawer;
        add(this.drawer);

        // Setup
        setResizable(true);
        pack();
        setTitle("Random Domino Tiling");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    /// Setup Method ///
    public static void setup(DominoDrawer _drawer) {
        EventQueue.invokeLater(() -> {
            // Create Self
            JFrame ex = new DominoInterface(_drawer);

            // Set Visibility
            ex.setVisible(true);
        });
    }
}