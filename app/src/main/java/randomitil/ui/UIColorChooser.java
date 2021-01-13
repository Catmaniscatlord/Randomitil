// Package Declaration
package randomitil.ui;

// Imports
import javax.swing.*;

// Class Declaration
public class UIColorChooser extends JColorChooser {
    // Serialization
    private static final long serialVersionUID = 3337828037218852291L;

    /// Constructor ///
    public UIColorChooser() {
        // Color Chooser default constructor
        super();
        
        // Remove Preview Panel
        this.setPreviewPanel(new JPanel());

        // Remove HSL, HSV, and CYMK Tabs
        removeChooserPanel(this.getChooserPanels()[2]);
        removeChooserPanel(this.getChooserPanels()[2]);
        removeChooserPanel(this.getChooserPanels()[2]);

    }
}