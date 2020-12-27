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
}
