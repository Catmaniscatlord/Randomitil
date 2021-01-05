package randomitil;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        DominoDrawer uiDrawer = new DominoDrawer();
        DominoInterface.setup(uiDrawer);
        
        long startTime = System.nanoTime();

        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond(2,2,1,1);
        
        DominoIteration kevin = new DominoIteration(diamond);
        kevin.setanimationMode(true);
        
        kevin.fillEmptySquares();
        System.out.println("new tiles");
        System.out.println(kevin.getAztecDiamond());
    
        for (int i = 0; i < 100; i++) {
            kevin.moveDominos();
            kevin.fillEmptySquares();
            //System.out.println("new tiles");
            //System.out.println(kevin.getAztecDiamond());
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
