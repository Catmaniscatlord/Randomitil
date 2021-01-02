package randomitil;

import java.util.Scanner;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        
        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond(2, 2);
        
        DominoIteration kevin = new DominoIteration(diamond);
        kevin.setanimationMode(true);
        
        for (int i = 0; i < 10; i++) {
            DominoDrawer ui_drawer = new DominoDrawer();
            DominoInterface.setup(ui_drawer);
            kevin.fillEmptySquares();
            //kevin.fillEmptyTiles();
            System.out.println("generated tiles");
            System.out.println(kevin.getGeneratedTiles());
            System.out.println("new tiles");
            System.out.println(kevin.getAztecDiamond());
            ui_drawer.updateDiamond(kevin.getAztecDiamond(), kevin, kevin.getAztecDiamond().getSize());
            if (kevin.getAztecDiamond().checkTiles().isEmpty()) {
                System.out.println("we gucci");
            } else {
                System.out.println("oh booty cheeks");
            }
            kevin.moveDominos(1);
            System.out.println("Removed tiles");
            System.out.println(kevin.getRemovedTiles());
            System.out.println("moved tiles");
            System.out.println(kevin.getAztecDiamond());
       }
    }
}
