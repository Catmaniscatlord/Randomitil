package randomitil;

import java.util.HashSet;
import java.util.Set;

public class AztecDiamond {

    Domino[][] tiles;

    private int width;
    private int height;
    private int size;

    public AztecDiamond() {
        this(2, 2);
    }

    public AztecDiamond(int width, int height) {
        createTiles(width, height);
    }

    private void createTiles(int width, int height) {
        this.size = (width <= height ? height : width);
        this.tiles = new Domino[this.size][this.size];
        this.width = width;
        this.height = height;
        chamferTiles();
    }

    private void chamferTiles() {
        int chamferSize = Math.abs(this.width - this.height);
        boolean[][] chamferArray = new boolean[chamferSize][];
        // this creates an array of decreasing subarray size
        for (int i = 0; i < chamferSize; i++) {
            chamferArray[i] = new boolean[chamferSize - i];
        }
        if (this.width < this.height) {
            for (int i = 0; i < chamferArray.length; i++) {
                for (int j = 0; j < chamferArray[i].length; j++) {
                    this.tiles[i][j] = new Domino(false);
                    this.tiles[this.size - i - 1][this.size - j - 1] = new Domino(false);
                }
            }
        } else {
            for (int i = 0; i < chamferArray.length; i++) {
                for (int j = 0; j < chamferArray[i].length; j++) {
                    this.tiles[i][this.size - j - 1] = new Domino(false);
                    this.tiles[this.size - i - 1][j] = new Domino(false);
                }
            }
        }
    }

    public void expand(int size) {

        /*
         * this will remove all nonplaceable tiles before we expand non-placeable tiles
         * only exist if width and height arent equal
         */
        if (this.width != this.height) {
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (tiles[i][j] != null) {
                        if (!tiles[i][j].isPlaceable())
                            tiles[i][j] = null;
                    }
                }
            }
        }
        int newSize = this.size + 2 * size;
        expandWidth(size, this.size, newSize);
        expandHeight(size, this.size, newSize);
        this.size = newSize;
        chamferTiles();
    }

    private void expandWidth(int expansionSize, int oldSize, int newSize) {
        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[oldSize][newSize];

        for (int i = 0; i < oldSize; i++) {
            System.arraycopy(tempTiles[i], 0, tiles[i], expansionSize, oldSize);
        }
    }

    private void expandHeight(int expansionSize, int oldSize, int newSize) {
        Domino[][] tempTiles = this.tiles.clone();
        tiles = new Domino[newSize][newSize];

        System.arraycopy(tempTiles, 0, tiles, expansionSize, oldSize);
    }

    /*
     * This returns another AztecDiamond where all of the valid dominos are removed
     * dominos that are invalid will stay in the array
     * 
     * if the return is empty then the domino layout is valid
     */

    public AztecDiamond checkTiles() {
        AztecDiamond checkArray = new AztecDiamond(this.width, this.height);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
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

        /*
         * if the tile is null or is not a valid place to put a domino return 0
         */
        if (tile == null)
            return 0;
        if (!tile.isPlaceable())
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

        if (!hasNoNeighbors(x, y))
            return 1;

        return 0;
    }

    public Domino[][] getTileNeighbors(int x, int y) {
        Domino[][] neighbors = new Domino[3][3];
        int neighborX;
        int neighborY;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[i].length; j++) {
                neighborY = y + i - 1;
                neighborX = x + j - 1;
                if (neighborX >= 0 && neighborY >= 0 && neighborX < this.size && neighborY < this.size) {
                    if (((x + y) % 2 == 0 && ((i == 0 && j == 0) || (i == 2 && j == 2)))
                            || ((x + y) % 2 == 1 && ((i == 2 && j == 0) || (i == 0 && j == 2)))) {
                        neighbors[i][j] = new Domino(false);
                    } else
                        neighbors[i][j] = getTile(neighborX, neighborY);
                } else
                    neighbors[i][j] = new Domino(false);
            }
        }
        // this makes sure that we dont return the domino as one of its neighbors
        neighbors[1][1] = null;
        return neighbors;
    }

    public boolean hasNoNeighbors(int x, int y) {
        Set<Domino> neighbors = new HashSet<Domino>();
        Domino[][] tileNeighbors = getTileNeighbors(x, y);
        for (int i = 0; i < tileNeighbors.length; i++) {
            for (int j = 0; j < tileNeighbors[i].length; j++) {
                if (!(((x + y) % 2 == 0 && ((i == 0 && j == 0) || (i == 2 && j == 2)))
                        || ((x + y) % 2 == 1 && ((i == 2 && j == 0) || (i == 0 && j == 2))))) {
                    neighbors.add(tileNeighbors[i][j]);
                }
            }
        }
        /*
         * most of the time the neighbors will only contain null when it contains
         * another domino there ius a possiblity that the domino is a placeholder in the
         * array in which case we move on
         */
        if (neighbors.size() != 1) {
            for (Domino d : neighbors) {
                if (d.isPlaceable())
                    return false;
            }
        }
        return true;
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

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[\n");
        for (int i = 0; i < this.size; i++) {
            buf.append('[');
            for (int j = 0; j < this.size; j++) {
                Domino tile = getTile(i, j);
                if (tile != null) {
                    if (!tile.isPlaceable())
                        buf.append("####");
                    else if (tile.direction == Direction.LEFT)
                        buf.append(" <- ");
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
                if (j != this.size - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        buf.append(']');
        return buf.toString();
    }
}
