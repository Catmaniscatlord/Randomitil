package randomitil.tilings;

public abstract class Tile {
    
    public Direction direction;
    public boolean placeable;

    public Tile(boolean placeable) {
		this.placeable = placeable;
        this.direction = null;
    }

    public Tile(Direction direction) {
        this.direction = direction;
        this.placeable = true;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isPlaceable() {
        return placeable;
    }
}
