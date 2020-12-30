package randomitil;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        DominoDrawer ui_drawer = new DominoDrawer();
        DominoInterface.setup(ui_drawer);

        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond(4,6);
        
        System.out.println(diamond);
        DominoIteration kevin = new DominoIteration(diamond);
        kevin.generateSquares();
        System.out.println(diamond);
        
        // Update Diamond
        ui_drawer.updateDiamond(diamond, diamond, diamond.getSize());
    }
}
