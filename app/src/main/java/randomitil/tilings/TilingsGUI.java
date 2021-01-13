package randomitil.tilings;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public abstract class TilingsGUI extends JPanel {

    private static final long serialVersionUID = 1L;
    
    protected TilingsDrawer drawer;

    public TilingsGUI (String name) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                                                   name, TitledBorder.LEFT, TitledBorder.TOP));
        setUp();
    }

    abstract protected void setUp();
}
