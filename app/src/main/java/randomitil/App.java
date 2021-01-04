package randomitil;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        DominoDrawer ui_drawer = new DominoDrawer();
        DominoInterface.setup(ui_drawer);
        
        long startTime = System.nanoTime();

        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond(2,2,2,2);
        
        DominoIteration kevin = new DominoIteration(diamond);
        kevin.setanimationMode(true);
        
        kevin.fillEmptySquares();
        System.out.println("new tiles");
        System.out.println(kevin.getAztecDiamond());
    
        for (int i = 0; i < 200; i++) {
            kevin.moveDominos();
            kevin.fillEmptySquares();
            //System.out.println("new tiles");
            //System.out.println(kevin.getAztecDiamond());
        }
        
        if (!kevin.getAztecDiamond().checkTiles().isEmpty()) {
            System.out.println("oh booty cheeks");
        }
        ui_drawer.updateDiamond(kevin.getAztecDiamond(), kevin, kevin.getAztecDiamond().getSize());

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("time taken : " + timeElapsed/1000000);
    }
}
