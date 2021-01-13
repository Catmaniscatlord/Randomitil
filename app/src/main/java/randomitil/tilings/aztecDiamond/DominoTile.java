package randomitil.tilings.aztecDiamond;

import randomitil.tilings.*;

public class DominoTile extends Tile {
    public DominoTile(DominoDirection direction) {
        super(direction);
    }

    public DominoTile(Boolean placeable) {
        super(placeable);
    }

    @Override
    public String toString() {
        if (!this.placeable)
            return "####";
        else if (this.direction == DominoDirection.LEFT)
            return " <- ";
        else if (this.direction == DominoDirection.RIGHT)
            return " -> ";
        else if (this.direction == DominoDirection.UP)
            return "  ^ ";
        else if (this.direction == DominoDirection.DOWN)
            return " \\/ ";

        return "oops";
    }
}
