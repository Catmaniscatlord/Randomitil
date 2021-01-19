package randomitil;

import randomitil.animation.*;
import randomitil.ui.aztecDiamond.DiamondUI;

public class App {
    public static void main(String[] args) {   
        // Set up UI
        DiamondDrawer appDrawer = new DiamondDrawer();
        DiamondUI appUi = new DiamondUI(appDrawer);
        DiamondInterface.setup(appDrawer, appUi);
    
    }
}
 