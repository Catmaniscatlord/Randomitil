package randomitil;

import randomitil.animation.*;
import randomitil.tilings.aztecDiamond.*;
import randomitil.ui.*;

public class App {
    public static void main(String[] args) {
        for (int j = 0; j < 1; j++) {
            
            // Set up UI
            DiamondDrawer appDrawer = new DiamondDrawer();
            DiamondUI appUi = new DiamondUI(appDrawer);
            DiamondInterface.setup(appDrawer, appUi);

            long startTime = System.nanoTime();

            // Set up Diamond for rendering
            DiamondTilings diamond = new DiamondTilings();
            
            DiamondIteration kevin = new DiamondIteration(diamond);
            kevin.setAnimationMode(false);

            kevin.setTilingBias(0.5);
            kevin.fillBlankSpaces();
            System.out.println("new tiles");
            System.out.println(kevin.getTiling());
        
            for (int i = 0; i < 20; i++) {
                kevin.iterateTiles();       
            }
            
            if (!kevin.getTiling().checkTiles().isEmpty()) {
                System.out.println("oh booty cheeks");
            }
            appDrawer.updateTiling((DiamondTilings) kevin.getTiling(), kevin, kevin.getTiling().getSize());

            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            System.out.println("time taken : " + timeElapsed/1000000);
        }
    }
}
 