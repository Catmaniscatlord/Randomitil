package randomitil;


public class Domino
{    
    Direction direction;
    boolean moved;
    boolean placeable;

    public Domino()
    {
        this(Direction.UP,false,true);
    }

    public Domino(boolean placeable) {
        this(null,false,false);
    }

    public Domino(Direction direction, boolean moved, boolean placeable)
    {
        this.direction = direction;
        this.moved = moved;
        this.placeable = placeable;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isPlaceable() {
        return placeable;
    }

    public void setPlaceable(boolean placeable) {
        this.placeable = placeable;
    }
}
