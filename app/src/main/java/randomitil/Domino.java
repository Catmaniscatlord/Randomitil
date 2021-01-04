package randomitil;


public class Domino
{    
    Direction direction;
    boolean placeable;

    public Domino()
    {
        this(null,true);
    }

    public Domino(boolean placeable) {
        this(null, placeable);
    }

    public Domino(Direction direction, boolean placeable)
    {
        this.direction = direction;
        this.placeable = placeable;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isPlaceable() {
        return placeable;
    }

    public void setPlaceable(boolean placeable) {
        this.placeable = placeable;
    }

    @Override
    public String toString() {
        if (!this.placeable)
            return "####";
        else if (this.direction == Direction.LEFT)
            return " <- ";
        else if (this.direction == Direction.RIGHT)
            return " -> ";
        else if (this.direction == Direction.UP)
            return "  ^ ";
        else if (this.direction == Direction.DOWN)
            return " \\/ ";

        return "oops";
    }
}
