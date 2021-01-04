package randomitil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AztecDiamond {

    Domino[][] tiles;

    private int width;
    private int height;
    private int size;
    private int expansionRate;
    
    int iteration;
    int widthRate;
    int heightRate;

    public AztecDiamond() {
        this(2, 2);
    }

    public AztecDiamond(int width, int height) {
        this(width, height, 1, 1);
    }
    
    public AztecDiamond(int width, int height, int widthRate, int heightRate){
        createTiles(width, height);
        setHeightRate(heightRate);
        setWidthRate(widthRate);
        this.iteration = 0;
    }

    private void createTiles(int width, int height) {
        this.size = Math.max(width, height);
        this.tiles = new Domino[this.size][this.size];
        this.width = width;
        this.height = height;
    }

    public void setUpTiles() {
        if(this.width == this.height) {
            if(this.widthRate == this.heightRate) {
                if(this.expansionRate != 1){
                    sameSizeRateChamfer();
                }
            }
        }
    }

    private void sameSizeRateChamfer() {
        boolean[][] chamfer = new boolean[2 * (this.expansionRate - 1)][4 * (this.expansionRate - 1)];
        
        for (int i = 0; i < chamfer.length ; i++) {
            for (int j = 0; j < chamfer[i].length/2; j++) {
                if(j - i >= 0) {
                    chamfer[i][j] = true;
                    chamfer[i][chamfer[i].length - j - 1] = true;
                }
                else { 
                    chamfer[i][j] = false;
                    chamfer[i][chamfer[i].length - j - 1] = false;
                }
            }
        }
        
        int offset;
        Domino dom = new Domino(false);

        for (int k = 0; k < this.iteration; k++) {
            offset = 2 + k * (2 + chamfer[1].length);
            for (int i = 0; i < chamfer.length; i++) {
                for (int j = 0; j < chamfer[i].length; j++) {
                    if(chamfer[i][j]){
                        this.tiles[i][j + offset]                 = dom; // top
                        this.tiles[this.size - i - 1][j + offset] = dom; // bottom
                        this.tiles[j + offset][i]                 = dom; // left
                        this.tiles[j + offset][this.size - i - 1] = dom; // right
                    }
                }
            }
        }
    }

    public void expand() {
        /*
         * this will remove all nonplaceable tiles before we expand non-placeable tiles
         * only exist if width and height arent equal
         */
        if (this.width != this.height) {
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (tiles[i][j] != null && !tiles[i][j].isPlaceable()) {
                        tiles[i][j] = null;
                    }
                }
            }
        }
        int newSize = this.size + (2 * (2 * this.expansionRate - 1));
        expandWidth(2 * this.expansionRate - 1, this.size, newSize);
        expandHeight(2 * this.expansionRate - 1, this.size, newSize);
        this.size = newSize;
        this.width += 2 * (2 * this.widthRate - 1); 
        this.height += 2 * (2 * this.heightRate - 1);
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

    public int checkTile(int y, int x) {

        Domino tile = getTile(y, x);

        /*
         * if the tile is null or is not a valid place to put a domino return 0
         */
        if (tile == null)
            return 0;
        if (!tile.isPlaceable())
            return 0;

        Direction tileDirection = tile.getDirection();
        if (getOrientation(y, x) == Orientation.HORIZONTAL) {
            if (!(tileDirection == Direction.UP || tileDirection == Direction.DOWN)) {
                return 1;
            }
        } else {
            if (!(tileDirection == Direction.LEFT || tileDirection == Direction.RIGHT)) {
                return 1;
            }
        }
        // this checks that all of its direct neighbors are empty

        if (tileHasNeighbors(y, x))
            return 1;

        return 0;
    }

    public Domino[][] getTileNeighbors(int y, int x) {
        Domino[][] neighbors = new Domino[3][3];
        int neighborX;
        int neighborY;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[i].length; j++) {
                neighborY = y + i - 1;
                neighborX = x + j - 1;
                if (neighborX >= 0 && neighborY >= 0 && neighborX < this.size && neighborY < this.size) {
                    neighbors[i][j] = getTile(neighborY, neighborX);
                } else
                    neighbors[i][j] = new Domino(false);
            }
        }
        // this makes sure that we dont return the domino as one of its neighbors
        neighbors[1][1] = null;
        return neighbors;
    }

    public boolean tileHasNeighbors(int y, int x) {
        Set<Domino> neighbors = new HashSet<Domino>();
        Domino[][] tileNeighbors = getTileNeighbors(y, x);
        for (int i = 0; i < tileNeighbors.length; i++) {
            for (int j = 0; j < tileNeighbors[i].length; j++) {
                if (!((getOrientation(y, x) == Orientation.HORIZONTAL && ((i == 0 && j == 2) || (i == 2 && j == 0)))
                    || (getOrientation(y, x) == Orientation.VERTICAL && ((i == 0 && j == 0) || (i == 2 && j == 2))))) {
                    neighbors.add(tileNeighbors[i][j]);
                }
            }
        }
        /*
         * most of the time the neighbors will only contain null when it contains
         * another domino there is a possiblity that the domino is a placeholder in the
         * array in which case we move on
         */
        if (neighbors.size() != 1) {
            for (Domino d : neighbors) {
                if(d != null && d.isPlaceable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isEmpty() {
        Boolean isEmpty = true;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (getTile(i, j) != null && getTile(i, j).isPlaceable()) {
                    isEmpty = false;
                    return isEmpty;                        
                }
            }
        }
        return isEmpty;
    }

    public Orientation getOrientation(int y, int x) {
        if ((y + x) % 2 == (this.width < this.height ? 0 : 1))
            return Orientation.HORIZONTAL;
        return Orientation.VERTICAL;
    }

    public void setTile(int y, int x, Domino domino) {
        tiles[y][x] = domino;
    }

    public Domino getTile(int y, int x) {
        return tiles[y][x];
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

    public int getHeightRate() {
        return heightRate;
    }

    public void setHeightRate(int heightRate) {
        this.heightRate = heightRate;
        this.expansionRate =  Math.max(this.heightRate, this.widthRate);
    }

    public int getWidthRate() {
        return widthRate;
    }

    public void setWidthRate(int widthRate) {
        this.widthRate = widthRate;
        this.expansionRate =  Math.max(this.heightRate, this.widthRate);
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.size; i++) {
            buf.append('[');
            for (int j = 0; j < this.size; j++) {
                Domino tile = getTile(i, j);
                if (tile != null) {
                    buf.append(tile.toString());
                } else {
                    buf.append("____");
                }
                if (j != this.size - 1)
                    buf.append(",");
            }
            buf.append("]\n");
        }
        return buf.toString();
    }
}
