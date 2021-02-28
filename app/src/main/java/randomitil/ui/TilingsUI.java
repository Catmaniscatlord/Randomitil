// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;

// Class Declaration
public class TilingsUI extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Class Objects
    protected TilingsDrawer drawer;
    protected EtchedBorder uiBorder;
    
    /// Constructor ///
    public TilingsUI(TilingsDrawer drawer) {
        // Setup Background
        setBackground(new Color (240, 240, 240));
        setPreferredSize(new Dimension(300, 300));

        // Setup Box Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Setup Border
        uiBorder = (EtchedBorder) BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        // setup variables
        this.drawer = drawer;

        // Add Control Panels
        this.addControlPanels();
    }

    /// Create Box Gaps Between Components
    protected void addBoxGap() {
        // Create Gap Dimensions
        Dimension maxGapSize = new Dimension(525, 10);
        Dimension preferredGapSize = new Dimension(420, 8);
        Dimension minGapSize = new Dimension(210, 4);

        // Return Filler
        this.add(new Box.Filler(minGapSize, preferredGapSize, maxGapSize));
    }

    /// Add Control Panels ///
    protected void addControlPanels() {
        // Subclass Fill in Options
    }
}