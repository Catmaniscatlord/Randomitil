package randomitil;


public class Domino
{    
    Direction direction;
    boolean moved;

    public Domino ()
    {
        this(Direction.UP,false);
    }

    public Domino (Direction direction, boolean moved)
    {
        this.direction = direction;
        this.moved = moved;
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
}
