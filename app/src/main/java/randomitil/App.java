package randomitil;

import java.util.Arrays;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        for (int j = 0; j < 1; j++) {
            
            // Set up UI
            DominoDrawer appDrawer = new DominoDrawer();
            DominoUI appUi = new DominoUI(appDrawer);
            DominoInterface.setup(appDrawer, appUi);

            appDrawer.setColorChange(true);
            appDrawer.setDisplaySize(300, 300);
            long startTime = System.nanoTime();

            // Set up Diamond for rendering
            AztecDiamond diamond = new AztecDiamond();
            
            DominoIteration kevin = new DominoIteration(diamond,1,1);
            kevin.setAnimationMode(false);

            kevin.fillEmptySquares();
            System.out.println("new tiles");
            System.out.println(kevin.getAztecDiamond());
        
            for (int i = 0; i < 1000; i++) {
                kevin.moveDominos();
                kevin.fillEmptySquares();        
            }
            
            if (!kevin.getAztecDiamond().checkTiles().isEmpty()) {
                System.out.println("oh booty cheeks");
            }
            appDrawer.updateDiamond(kevin.getAztecDiamond(), kevin, kevin.getAztecDiamond().getSize());

            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            System.out.println("time taken : " + timeElapsed/1000000);
        }
    }
}
 