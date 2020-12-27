package randomitil;

import java.util.HashSet;
import java.util.Set;

public class AztecDiamond {
    Domino[][] tiles;
    int width;
    int height;

    public AztecDiamond() {
        this(2, 2);
    }

    public AztecDiamond(int width, int height) {
        tiles = new Domino[height][width];
        this.width = width;
        this.height = height;
    }

    public void expand(int size) {
        expandWidth(size);
        expandHeight(size);
    }

    private void expandWidth(int width) {
        int originalWidth = this.width;
        this.width += 2 * width;

        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            System.arraycopy(tempTiles[i], 0, tiles[i], width, originalWidth);
        }
    }

    private void expandHeight(int height) {
        int originalHeight = this.height;
        this.height += 2 * height;

        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[this.height][this.width];

        System.arraycopy(tempTiles, 0, tiles, height, originalHeight);
    }

    /*
     * This returns another AztecDiamond where all of the valid dominos are removed
     * dominos that are invalid will stay in the array
     * 
     * if the return is empty then the domino layout is valid
     **/

    public AztecDiamond checkTiles() {
        AztecDiamond checkArray = new AztecDiamond(this.width, this.height);

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (checkTile(i, j) != 0) {
                    checkArray.setTile(i, j, getTile(i, j));
                }
            }
        }
        return checkArray;
    }

    /*
     * returns 0 if the check passes, 1 if there is an error
     **/

    public int checkTile(int x, int y) {

        Domino tile = getTile(x, y);
        if (tile == null)
            return 0;

        Direction tileDirection = tile.getDirection();
        if ((x + y) % 2 == 0) {
            if (!(tileDirection == Direction.UP || tileDirection == Direction.DOWN)) {
                return 1;
            }
        } else {
            if (!(tileDirection == Direction.LEFT || tileDirection == Direction.RIGHT)) {
                return 1;
            }
        }
        // this checks that all of its direct neighbors are empty
        if (getTileNeighobors(x, y).size() != 1) {
            return 1;
        }
        return 0;
    }

    public Set<Domino> getTileNeighobors(int x, int y) {
        Set<Domino> neighbors = new HashSet<Domino>();
        if (x != 0)
            neighbors.add(getTile(x - 1, y));
        if (y != 0)
            neighbors.add(getTile(x, y - 1));
        if (x != this.width)
            neighbors.add(getTile(x + 1, y));
        if (y != this.height)
            neighbors.add(getTile(x, y + 1));
        if (x + y % 2 == 0) {
            if (x != 0 && y != this.height)
                neighbors.add(getTile(x - 1, y + 1));
            if (x != this.width && y != 0)
                neighbors.add(getTile(x + 1, y - 1));
        } else {
            if (x != 0 && y != 0)
                neighbors.add(getTile(x - 1, y - 1));
            if (x != this.width && y != this.height)
                neighbors.add(getTile(x + 1, y + 1));
        }

        return neighbors;
    }

    public Boolean isEmpty() {
        Boolean isEmpty = true;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (getTile(i, j) != null) {
                    isEmpty = false;
                    return isEmpty;
                }
            }
        }
        return isEmpty;
    }

    public void setTile(int x, int y, Domino domino) {
        tiles[x][y] = domino;
    }

    public Domino getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTiles(Domino[][] tiles) {
        this.tiles = tiles;
    }

    public Domino[][] getTiles() {
        return tiles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[\n");
        for (int i = 0; i < this.width; i++) {
            buf.append('[');
            for (int j = 0; j < this.height; j++) {
                Domino tile = getTile(i, j);
                if (tile != null) {
                    if (tile.direction == Direction.LEFT)
                        buf.append(" <- "); // →
                    else if (tile.direction == Direction.RIGHT)
                        buf.append(" -> ");
                    else if (tile.direction == Direction.UP)
                        buf.append("  ^ ");
                    else if (tile.direction == Direction.DOWN)
                        buf.append(" \\/ ");
                    else
                        buf.append("oops");
                } else {
                    buf.append("null");
                }
                if (j != this.height - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        buf.append(']');
        return buf.toString();
    }
}
