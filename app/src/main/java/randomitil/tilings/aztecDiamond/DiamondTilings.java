package randomitil.tilings.aztecDiamond;

import randomitil.tilings.*;

import java.util.HashSet;
import java.util.Set;

public class DiamondTilings extends Tilings{
    
    public DiamondTilings() {
        this(2);
    }

    public DiamondTilings(int size) {
        createTiles(size);
    }
    
    public void createTiles(int size) {
        this.tiles = new Tile[size][size];
    }

    @Override
    public void setUpTiles() {
        
    }
    
    @Override
    public DominoTile[][] getTileNeighbors(int y, int x) {
        DominoTile[][] neighbors = new DominoTile[3][3];
        int neighborX;
        int neighborY;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[i].length; j++) {
                neighborY = y + i - 1;
                neighborX = x + j - 1;
                if (neighborX >= 0 && neighborY >= 0 && neighborX < this.size && neighborY < this.size) {
                    neighbors[i][j] = (DominoTile) getTile(neighborY, neighborX);
                } else
                    neighbors[i][j] = new DominoTile(false);
            }
        }
        // this makes sure that we dont return the domino as one of its neighbors
        neighbors[1][1] = null;
        return neighbors;
    }

    @Override
    public boolean tileHasNeighbors(int y, int x) {
        Set<Tile> neighbors = new HashSet<Tile>();
        DominoTile[][] tileNeighbors = getTileNeighbors(y, x);
        for (int i = 0; i < tileNeighbors.length; i++) {
            for (int j = 0; j < tileNeighbors[i].length; j++) {
                if (!((getOrientation(y, x) == DominoOrientation.HORIZONTAL && ((i == 0 && j == 2) || (i == 2 && j == 0)))
                    || (getOrientation(y, x) == DominoOrientation.VERTICAL && ((i == 0 && j == 0) || (i == 2 && j == 2))))) {
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
            for (Tile t : neighbors) {
                if(t != null && t.isPlaceable()) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public Orientation getOrientation(int y, int x) {
        if ((y + x) % 2 == 1)
            return DominoOrientation.HORIZONTAL;
        return DominoOrientation.VERTICAL;
    }

    @Override
    public Tilings checkTiles() {
        Tilings checkArray = new DiamondTilings(this.size);

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
    @Override
    public int checkTile(int y, int x) {
        DominoTile tile = (DominoTile) getTile(y, x);

        /*
         * if the tile is null or is not a valid place to put a domino return 0
         */
        if (tile == null)
            return 0;
        if (!tile.isPlaceable())
            return 0;

        Direction tileDirection = tile.getDirection();
        if (getOrientation(y, x) == DominoOrientation.HORIZONTAL) {
            if (!(tileDirection == DominoDirection.UP || tileDirection == DominoDirection.DOWN)) {
                return 1;
            }
        } else {
            if (!(tileDirection == DominoDirection.LEFT || tileDirection == DominoDirection.RIGHT)) {
                return 1;
            }
        }
        // this checks that all of its direct neighbors are empty

        if (tileHasNeighbors(y, x))
            return 1;

        return 0;
    }   

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.size; i++) {
            buf.append('[');
            for (int j = 0; j < this.size; j++) {
                DominoTile tile = (DominoTile) getTile(i, j);
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
