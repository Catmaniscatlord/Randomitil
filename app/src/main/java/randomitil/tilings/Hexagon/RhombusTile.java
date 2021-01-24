package randomitil.tilings.Hexagon;

import randomitil.tilings.Tile;

public class RhombusTile extends Tile{
    public RhombusTile (RhombusDirection direction) {
        super(direction);    
    }

    public RhombusTile(Boolean placeable) {
        super(placeable);
    }

    @Override
    public String toString() {
        if(!this.placeable) {
            return "###";
        }
        else if(this.direction == RhombusDirection.UP) {
            return " /\\";
        }
        else if(this.direction == RhombusDirection.UPLEFTDIAGONAL) {
            return " *\\";
        }
        else if(this.direction == RhombusDirection.UPRIGHTDIAGONAL) {
            return " /*";
        }
        else if(this.direction == RhombusDirection.DOWN) {
            return " \\/";
        }
        else if(this.direction == RhombusDirection.DOWNLEFTDIAGONAL) {
            return " _/";
        }
        else if(this.direction == RhombusDirection.DOWNRIGHTDIAGONAL) {
            return " \\_";
        }

        return "oof";
    }
}
