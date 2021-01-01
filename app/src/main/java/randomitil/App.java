package randomitil;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        DominoDrawer ui_drawer = new DominoDrawer();
        DominoInterface.setup(ui_drawer);

        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond(2,3);
        
        System.out.println(diamond);
        DominoIteration kevin = new DominoIteration(diamond);
        kevin.fillEmptySquares();
        System.out.println(diamond);
        kevin.fillEmptyTiles();
        System.out.println(diamond);
        kevin.moveDominos(1);
        diamond = kevin.getAztecDiamond();
        System.out.println(diamond);
        if(diamond.checkTiles().isEmpty()) {
            System.out.println("we gucci");
        }
        else {
            System.out.println("oh booty cheeks");
            System.out.println(diamond.checkTiles());
        }
        
        // Update Diamond
        ui_drawer.updateDiamond(diamond, kevin, diamond.getSize());
    }
}
