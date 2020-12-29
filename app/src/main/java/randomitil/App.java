package randomitil;

import randomitil.animation.*;

public class App {
    public static void main(String[] args) {
        // Set up UI
        DominoDrawer ui_drawer = new DominoDrawer();
        DominoInterface.setup(ui_drawer);

        // Set up Diamond for rendering
        AztecDiamond diamond = new AztecDiamond();
        diamond.expand(2);
        Domino dom1 = new Domino(Direction.DOWN, false, true);
        Domino dom2 = new Domino(Direction.RIGHT, false, true);
        Domino dom3 = new Domino(Direction.LEFT, false, true);

        //diamond.setTile(2, 1, dom1);
        //diamond.setTile(2, 4, dom2);
        diamond.setTile(1, 1, dom3);
        diamond.setTile(0, 0, dom3);

        // Update Diamond
        ui_drawer.updateDiamond(diamond, diamond, diamond.getSize());
        System.out.println(diamond);
    }
}
