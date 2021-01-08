// Package Declaration
package randomitil.animation;

// Imports
import java.awt.*;
import javax.swing.*;
import randomitil.*;
import java.awt.FlowLayout;
import java.lang.Runnable;
import java.lang.Math;

// Class Declaration
public class DominoUI extends JPanel implements Runnable {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Class Objects
    Thread uiThread;
    DominoDrawer drawer;
    
    /// Constructor ///
    public DominoUI(DominoDrawer drawer) {
        // Setup Background
        setBackground(new Color (235, 235, 235));
        setPreferredSize(new Dimension(300, 300));

        // setup variables
        this.drawer = drawer;
    }

    /// Initialization Work ///
    public void addNotify() {
        // Super Method
        super.addNotify();
        
        // Start Thread
        uiThread = new Thread(this);
        uiThread.start();
    }

    /// Run Method ///
    public void run() {
        //
    }
}