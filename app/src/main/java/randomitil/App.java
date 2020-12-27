package randomitil;

import randomitil.anim_test.*;

public class App {
    public static void main(String[] args) {
    
        DominoDrawer.setup("Graphics Test");

        AztecDiamond tiles = new AztecDiamond();

        tiles.setTile(0, 0, new Domino(Direction.DOWN, false));
        tiles.setTile(0, 1, new Domino(Direction.LEFT, false));
        tiles.setTile(1, 0, new Domino(Direction.RIGHT, false));
        tiles.setTile(1, 1, new Domino(Direction.UP, false));


        System.out.println(tiles.toString());

        tiles.expand(3);
        System.out.println(tiles.toString());
    }
}
