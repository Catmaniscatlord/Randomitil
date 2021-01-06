package randomitil;

import java.util.Arrays;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        for (int j = 0; j < 1; j++) {
            
            // Set up UI
            DominoDrawer uiDrawer = new DominoDrawer();
            DominoInterface.setup(uiDrawer);
            
            uiDrawer.setDisplaySize(600);
            long startTime = System.nanoTime();

            // Set up Diamond for rendering
            AztecDiamond diamond = new AztecDiamond(2,2,2,1);
            
            DominoIteration kevin = new DominoIteration(diamond);
            kevin.setanimationMode(false);

            kevin.fillEmptySquares();
            System.out.println("new tiles");
            System.out.println(kevin.getAztecDiamond());
        
            for (int i = 0; i < 50; i++) {
                kevin.moveDominos();
                kevin.fillEmptySquares();
            }
            
            if (!kevin.getAztecDiamond().checkTiles().isEmpty()) {
                System.out.println("oh booty cheeks");
            }
            uiDrawer.updateDiamond(kevin.getAztecDiamond(), kevin, kevin.getAztecDiamond().getSize());

            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            System.out.println("time taken : " + timeElapsed/1000000);
        }
    }
}
