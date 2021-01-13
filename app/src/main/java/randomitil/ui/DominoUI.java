// Package Declaration
package randomitil.ui;

// Imports
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import randomitil.animation.*;

// Class Declaration
public class DominoUI extends JPanel {
    // Serialization
    private static final long serialVersionUID = 3327828037218852291L;

    // Class Objects
    DominoDrawer drawer;
    EtchedBorder uiBorder;
    
    /// Constructor ///
    public DominoUI(DominoDrawer drawer) {
        // Setup Background
        setBackground(new Color (240, 240, 240));
        setPreferredSize(new Dimension(300, 300));

        // Setup FlowLayout
        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        // Setup Border
        uiBorder = (EtchedBorder) BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        // setup variables
        this.drawer = drawer;

        // Add Control Panels
        this.add(new UIGenPanel(drawer, uiBorder));
        this.add(new UIColorPanel(drawer, uiBorder));
        this.add(new UIColorChangePanel(drawer, uiBorder));
    }
}